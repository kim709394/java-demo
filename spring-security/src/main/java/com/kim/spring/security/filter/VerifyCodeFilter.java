package com.kim.spring.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kim.spring.security.common.ErrorEnum;
import com.kim.spring.security.exception.VerifyCodeException;
import com.kim.spring.security.pojo.ResultVO;
import com.kim.spring.security.service.UserService;
import com.kim.spring.security.utils.HttpResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author huangjie
 * @description  验证码验证过滤器,此过滤器继承OncePerRequestFilter对象，确保只有一次
 * 执行，不会重复执行
 * @date 2022/4/22
 */
@Component
public class VerifyCodeFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;
    @Autowired
    @Lazy
    private ObjectMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //判断是否是登录请求，只有登录之前需要校验验证码
        if(request.getRequestURI().equals("/login") &&
        request.getMethod().equalsIgnoreCase("POST")){
            String verifyCode = request.getParameter("verifyCode");
            String remoteAddr = request.getRemoteAddr();
            Map<String, String> verifyCodeObj = userService.getVerifyCode(remoteAddr);
            if(verifyCodeObj == null){
                HttpResponseUtil.responseJson(response,mapper, ResultVO.result(ErrorEnum.CODE_3005));
                throw new VerifyCodeException("验证码过期");
            }
            //验证码过期时间，这里设置30秒过时
            String timestamp = verifyCodeObj.get("timestamp");
            long now = System.currentTimeMillis();
            if((now - Long.valueOf(timestamp))>30*1000L){
                HttpResponseUtil.responseJson(response,mapper, ResultVO.result(ErrorEnum.CODE_3005));
                throw new VerifyCodeException("验证码过期");
            }
            String verifyCodeInternal = verifyCodeObj.get("verifyCode");
            if(!verifyCodeInternal.equalsIgnoreCase(verifyCode)){
                HttpResponseUtil.responseJson(response,mapper, ResultVO.result(ErrorEnum.CODE_3005));
                throw new VerifyCodeException("验证码不正确");
            }
            userService.removeVerifyCode(remoteAddr);
            filterChain.doFilter(request,response);

        }else{
            //非登录请求直接跳到后面的过滤器
            filterChain.doFilter(request,response);
        }
    }


}
