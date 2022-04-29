package com.kim.spring.security.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kim.spring.security.common.ErrorEnum;
import com.kim.spring.security.filter.VerifyCodeFilter;
import com.kim.spring.security.pojo.ResultVO;
import com.kim.spring.security.service.impl.MyUserDetailServiceImpl;
import com.kim.spring.security.utils.HttpResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author huangjie
 * @description 登录、认证、授权、鉴权配置类
 * @date 2022/4/20
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //开启方法注解授权支持
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyUserDetailServiceImpl myUserDetailService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private VerifyCodeFilter verifyCodeFilter;

    /**
     * json转换器对象
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Spring Security 中，安全构建器 HttpSecurity 和 WebSecurity 的区别是 :
     * 1. WebSecurity 不仅通过 HttpSecurity 定义某些请求的安全控制，也通过其他方式定义其他某些
     * 请求可以忽略安全控制;
     * 2. HttpSecurity 仅用于定义需要安全控制的请求(当然 HttpSecurity 也可以指定某些请求不需要
     * 安全控制);
     * 3. 可以认为 HttpSecurity 是 WebSecurity 的一部分， WebSecurity 是包含 HttpSecurity 的更大
     * 的一个概念;
     * 4. 构建目标不同
     * WebSecurity 构建目标是整个 Spring Security 安全过滤器 FilterChainProxy`,
     * HttpSecurity 的构建目标仅仅是 FilterChainProxy 中的一个 SecurityFilterChain
     */

    @Override
    public void configure(WebSecurity web) throws Exception {
        //忽略静态资源,不能放行前端页面，否则sec标签不生效
        web.ignoring().antMatchers("/login1/**","/css/**","/js/**","/**.js");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /**
         * 自带的最简单原始的登录验证，所有请求的url都需要先进行登录认证
         * http.httpBasic()
         .and().authorizeRequests()
         .anyRequest().authenticated();
         *
         * remenber me功能原理，生成一个包含用户名密码信息的token存进客户端的cookie，以后验证的时候获取该cookie
         * 这种方式不推荐使用，因为会保留用户敏感信息到客户端
         */
        http
                //添加验证码过滤器，这个过滤器要在登录认证过滤器之前
                .addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    //未登录认证时的响应处理实现，默认跳转到登录页
                    HttpResponseUtil.responseJson(response,mapper, ResultVO.result(ErrorEnum.CODE_3004));
                })
                //.accessDeniedPage("/security/user/login")   //权限不足跳转页面，默认跳转到/error
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    //无权限访问响应处理实现，默认跳转到登录页
                    HttpResponseUtil.responseJson(response,mapper, ResultVO.result(ErrorEnum.CODE_3006));
                })
                .and()
                .formLogin().loginPage("/security/front/login")     //开启表单登录并设置登录页面
                .loginProcessingUrl("/login")   //登录处理url,默认是UsernamePasswordAuthenticationFilter过滤器处理
                .usernameParameter("username").passwordParameter("password")    //指定表单用户名和密码的参数名
                //.successForwardUrl("/security/front/index")     //指定登录成功后重定向跳转的url
                //.failureForwardUrl("/security/front/index")      //指定登录失败后重定向跳转的url
                .successHandler((request, response, authentication) -> {    //登录成功响应处理实现
                    HttpResponseUtil.responseJson(response, mapper, ResultVO.success());
                }).failureHandler((request, response, exception) -> {
                    //登录认证不通过响应处理实现，当抛出AuthenticationException异常的时候执行
                    HttpResponseUtil.responseJson(response, mapper, ResultVO.result(ErrorEnum.CODE_3003));
                }).and()
                .authorizeRequests().antMatchers("/security/front/login", "/security/user/verify/code")
                .permitAll()    //放行登录页面，验证码接口，任何人都可以访问
                //授权部分
                //.antMatchers("/security/user/del/{id}")
                //.access("@myAuthorizationService.check(authentication,request,#id)") //根据路径id来判断授权逻辑
                //.antMatchers("/security/user/list")
                //.hasAuthority("user:list")  //对于/security/user/list的访问需要user:list的权限
                //.antMatchers("/security/user/get/{id}")
                //.hasAnyAuthority("user:list","user:get")    //对于/security/user/get/{id}的访问需要user:list或者user:get的权限
                //.antMatchers("/security/user/**")
                //.hasRole("admin")   //授权了admin角色才能访问
                //.hasAnyRole("user","admin") //授权了"admin"和"user"角色任意一个才能访问
                //.access("hasAnyRole('user,admin') and hasIpAddress('127.0.0.1')")   //有user或者admin角色并且指定ip为127.0.0.1的用户才能访问
                //.hasIpAddress("127.0.0.1")  //指定ip为127.0.0.1的用户才能访问
                //.access("@myAuthorizationService.check(authentication,request)")    //使用自定义授权逻辑
                .anyRequest()   //所有请求
                //.denyAll()    其余所有请求任何人都不能访问
                //.anonymous()  匿名用户允许访问
                //.rememberMe()     指定已记住的用户允许访问
                //.fullyAuthenticated()     登录认证后才能访问，不包含anonymous和rememberMe
                .authenticated()      //其余所有请求都要进行登录认证才能访问
                .and()
                .logout()   //退出登录处理url和退出成功后跳转url的指定，默认LogoutFilter过滤器处理
                .logoutUrl("/logout")
                //.logoutSuccessUrl("/security/front/login")
                .logoutSuccessHandler((request, response, authentication) -> {
                    //退出成功后响应实现
                    HttpResponseUtil.responseJson(response, mapper, ResultVO.success());
                }).and().headers().frameOptions().sameOrigin()     // 允许iframe加载页面
                .and().rememberMe()   //开启remenber me功能，登录表单需要有name为remenber-me的参数
                .tokenValiditySeconds(604800)   //remenber me的token生效时间，这里设置为一周，单位为秒
                .rememberMeParameter("remenber-me")     //remenber me的表单参数名称
                .and()  //csrf，跨站请求伪造，即伪造登录用户认证身份对网站进行攻击
                .csrf().disable()   //关闭csrf防护,这里的防护仅仅只是页面增加一个_csrf的参数，后台进行验证
                .cors().configurationSource(request -> {//允许跨域配置
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.addAllowedOrigin("*"); // 设置允许跨域的站点
                    corsConfiguration.addAllowedMethod("*"); //设置允许跨域的http方法
                    corsConfiguration.addAllowedHeader("*"); // 设置允许跨域的请求头
                    corsConfiguration.setAllowCredentials(true); // 允许带凭证
                    //对所有的url生效
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", corsConfiguration);
                    return source.getCorsConfiguration(request);
        })
                .and()
                .sessionManagement()
                //.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //设置会话创建策略
                //.invalidSessionUrl("/security/front/login")     //设置session无效后的跳转地址，默认是登录页
                .maximumSessions(1) //设置session最大会话数量，即同时在线的同一用户数量，后面登录的用户将会踢掉前面登录的用户
                //.expiredUrl("/security/front/login")     //设置session过期后的跳转路径，默认是登录页
                .maxSessionsPreventsLogin(false);    //当同一用户达到最大登录会话数时，是否禁止登录，false为不禁止


    }

    //配置自定义登录认证实现
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService);
    }


}
