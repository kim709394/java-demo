package com.kim.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

/**
 * @author huangjie
 * @description 启动类
 * @date 2022/4/20
 */
@SpringBootApplication(exclude = RedisAutoConfiguration.class)
public class SpringSecurityApplication {
    /**
     * 1. org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter
     * 根据请求封装获取WebAsyncManager，从WebAsyncManager获取/注册的安全上下文可调
     * 用处理拦截器
     * 2. org.springframework.security.web.context.SecurityContextPersistenceFilter
     * SecurityContextPersistenceFilter主要是使用SecurityContextRepository在session中保存
     * 或更新一个SecurityContext，并将SecurityContext给以后的过滤器使用，来为后续fifilter
     * 建立所需的上下文。SecurityContext中存储了当前用户的认证以及权限信息。
     * 3. org.springframework.security.web.header.HeaderWriterFilter
     * 向请求的Header中添加相应的信息,可在http标签内部使用security:headers来控制
     * 4. org.springframework.security.web.csrf.CsrfFilter
     * csrf又称跨域请求伪造，SpringSecurity会对所有post请求验证是否包含系统生成的csrf的
     * token信息，如果不包含，则报错。起到防止csrf攻击的效果。
     * 5. org.springframework.security.web.authentication.logout.LogoutFilter
     * 匹配URL为/logout的请求，实现用户退出,清除认证信息。
     * 6. org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
     * 表单认证操作全靠这个过滤器，默认匹配URL为/login且必须为POST请求。
     * 7. org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter
     * 如果没有在配置文件中指定认证页面，则由该过滤器生成一个默认认证页面。
     * 8. org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter
     * 由此过滤器可以生产一个默认的退出登录页面
     * 9. org.springframework.security.web.authentication.www.BasicAuthenticationFilter
     * 此过滤器会自动解析HTTP请求中头部名字为Authentication，且以Basic开头的头信息。
     * 10. org.springframework.security.web.savedrequest.RequestCacheAwareFilter
     * 通过HttpSessionRequestCache内部维护了一个RequestCache，用于缓存
     * HttpServletRequest
     * 11. org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter
     * 针对ServletRequest进行了一次包装，使得request具有更加丰富的API
     * 12. org.springframework.security.web.authentication.AnonymousAuthenticationFilter
     * 当SecurityContextHolder中认证信息为空,则会创建一个匿名用户存入到
     * SecurityContextHolder中。spring security为了兼容未登录的访问，也走了一套认证流程，
     * 只不过是一个匿名的身份。
     * 13. org.springframework.security.web.session.SessionManagementFilter
     * securityContextRepository限制同一用户开启多个会话的数量
     * 14. org.springframework.security.web.access.ExceptionTranslationFilter
     * 异常转换过滤器位于整个springSecurityFilterChain的后方，用来转换整个链路中出现的异
     * 常
     * 15. org.springframework.security.web.access.intercept.FilterSecurityInterceptor
     * 获取所配置资源访问的授权信息，根据SecurityContextHolder中存储的用户信息来决定其
     * 是否有权限。
     * Spring Security默认加载15个过滤器, 但是随着配置可以增加或者删除一些过滤器.
     * */

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class);
    }






}
