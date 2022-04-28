package com.kim.spring.security.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @author huangjie
 * @description  自定义授权验证逻辑
 * @date 2022/4/27
 */
@Component
public class MyAuthorizationService {





    public boolean check(Authentication authentication, HttpServletRequest request) {
        //验证当前用户是admin则放行，否则要有管理员角色才放行
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        if (username.equals("admin")) {
            return true;
        }else{
            for(GrantedAuthority authority: user.getAuthorities()){
                if(authority.getAuthority().equals("ROLE_admin")|| authority.getAuthority().equals("ROLE_user")){
                    return true;
                }
            }
        }
        return false;
    }

    //rest风格路径根据路径id进行授权判断
    public boolean check(Authentication authentication, HttpServletRequest request,Integer id){

        return id > 10;

    }
}
