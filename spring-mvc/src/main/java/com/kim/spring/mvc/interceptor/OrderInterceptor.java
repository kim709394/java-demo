package com.kim.spring.mvc.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huangjie
 * @description  mvc拦截器
 * @date 2022/3/11
 */
public class OrderInterceptor implements HandlerInterceptor {

    /**
     * 如果有多个拦截器，则拦截顺序：
     * interceptor1(preHandle) -> interceptor2(preHandle)
     * interceptor2(postHandle) -> interceptor1(postHandle)
     * interceptor2(afterCompletion) -> interceptor1(afterCompletion)
     * */


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入handler前置方法");
        //返回true则拦截通过，返回false则不通过
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("handler执行完毕，转发试图之前执行");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("转发试图执行完毕，响应客户端之前执行");
    }
}
