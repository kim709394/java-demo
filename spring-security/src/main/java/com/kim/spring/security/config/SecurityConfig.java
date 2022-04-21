package com.kim.spring.security.config;

import com.kim.spring.security.service.impl.MyUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author huangjie
 * @description  登录、认证、授权、鉴权配置类
 * @date 2022/4/20
 */
@SpringBootConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyUserDetailServiceImpl myUserDetailService;
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
                .formLogin().loginPage("/security/front/login")     //开启表单登录并设置登录页面
                .loginProcessingUrl("/login")   //登录处理url
                .usernameParameter("username").passwordParameter("password")    //指定表单用户名和密码的参数名
                .successForwardUrl("/security/front/index")     //指定登录成功后重定向跳转的url
                .failureForwardUrl("")      //指定登录失败后重定向跳转的url
                .and()
                .authorizeRequests().antMatchers("/security/front/login").permitAll()   //放行登录页面
                .anyRequest().authenticated()      //其余所有请求都要进行登录认证才能访问
                .and()
                .headers().frameOptions().sameOrigin()     // 允许iframe加载页面
                .and()
                .rememberMe()   //开启remenber me功能，登录表单需要有name为remenber-me的参数
                .tokenValiditySeconds(604800)   //remenber me的token生效时间，这里设置为一周，单位为秒
                .rememberMeParameter("remenber-me")     //remenber me的表单参数名称
                .csrf().disable()   //关闭csrf防护,这里的防护仅仅只是页面增加一个_csrf的参数，后台进行验证


    }

    //配置自定义登录认证实现
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService);
    }
}
