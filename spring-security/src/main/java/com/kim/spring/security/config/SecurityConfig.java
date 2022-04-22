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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author huangjie
 * @description  登录、认证、授权、鉴权配置类
 * @date 2022/4/20
 */
@SpringBootConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyUserDetailServiceImpl myUserDetailService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private VerifyCodeFilter verifyCodeFilter;

    /**
     * json转换器对象
     * */
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
     * */

    @Override
    public void configure(WebSecurity web) throws Exception {
        //忽略静态资源
        web.ignoring().antMatchers("/login1/**");
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
                /*.exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    //未登录或者无权限访问的响应处理实现，默认跳转到登录页
                    HttpResponseUtil.responseJson(response,mapper, ResultVO.result(ErrorEnum.CODE_3005));
                })*/
                .formLogin().loginPage("/security/front/login")     //开启表单登录并设置登录页面
                .loginProcessingUrl("/login")   //登录处理url,默认是UsernamePasswordAuthenticationFilter过滤器处理
                .usernameParameter("username").passwordParameter("password")    //指定表单用户名和密码的参数名
                //.successForwardUrl("/security/front/index")     //指定登录成功后重定向跳转的url
                //.failureForwardUrl("/security/front/index")      //指定登录失败后重定向跳转的url
                .successHandler((request, response, authentication) -> {    //登录成功响应处理实现
                    HttpResponseUtil.responseJson(response,mapper, ResultVO.success());
                })
                .failureHandler((request, response, exception) -> {
                    //登录认证不通过响应处理实现，当抛出AuthenticationException异常的时候执行
                    HttpResponseUtil.responseJson(response,mapper, ResultVO.result(ErrorEnum.CODE_3003));
                })
                .and()//放行登录页面，验证码接口
                .authorizeRequests().antMatchers("/security/front/login","/security/user/verify/code").permitAll()
                .anyRequest().authenticated()      //其余所有请求都要进行登录认证才能访问
                .and()
                .logout()   //退出登录处理url和退出成功后跳转url的指定，默认LogoutFilter过滤器处理
                .logoutUrl("/logout")
                //.logoutSuccessUrl("/security/front/login")
                .logoutSuccessHandler((request, response, authentication) -> {
                    //退出成功后响应实现
                    HttpResponseUtil.responseJson(response,mapper,ResultVO.success());
                })
                .and()
                .headers().frameOptions().sameOrigin()     // 允许iframe加载页面
                .and()
                .rememberMe()   //开启remenber me功能，登录表单需要有name为remenber-me的参数
                .tokenValiditySeconds(604800)   //remenber me的token生效时间，这里设置为一周，单位为秒
                .rememberMeParameter("remenber-me")     //remenber me的表单参数名称
                .and()
                .csrf().disable();   //关闭csrf防护,这里的防护仅仅只是页面增加一个_csrf的参数，后台进行验证


    }

    //配置自定义登录认证实现
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService);
    }


}
