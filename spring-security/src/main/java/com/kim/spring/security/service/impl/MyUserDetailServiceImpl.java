package com.kim.spring.security.service.impl;

import com.kim.spring.security.pojo.User;
import com.kim.spring.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author huangjie
 * @description  自定义登录认证实现
 * @date 2022/4/21
 */
@Service
public class MyUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    //登录验证器通过此方法获取UserDetails对象，这个对象返回用户名和密码，spring验证器将会对传过来的密码和UserDetails
    //的进行比对
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.getUserByName(username);
        if(user == null ){
            throw new UsernameNotFoundException(username);
        }
        //此用户的所有权限集合
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if(username.equals("admin")){
            //带ROLE_前缀的是角色，没有ROLE_前缀的是权限
            authorities.add(new SimpleGrantedAuthority("ROLE_user"));
            authorities.add(new SimpleGrantedAuthority("ROLE_admin"));
            //添加权限
            authorities.add(new SimpleGrantedAuthority("user:list"));
            authorities.add(new SimpleGrantedAuthority("user:add"));
            authorities.add(new SimpleGrantedAuthority("user:update"));
            authorities.add(new SimpleGrantedAuthority("user:del"));
            authorities.add(new SimpleGrantedAuthority("user:get"));
        }else{
            authorities.add(new SimpleGrantedAuthority("ROLE_user"));
            authorities.add(new SimpleGrantedAuthority("user:list"));
        }

        //返回用户信息
        return new org.springframework.security.core.userdetails.User(user.getName(),
                "{bcrypt}"+user.getPassword(),        //{noop}表示密码不加密认证，{bcrypt}表示加密认证
                true,   //是否启用该用户,true表示启用
                true,   //用户是否过期，true表示未过期
                true,   //用户凭证是否过期，true表示未过期
                true,       //用户是否锁定，true表示未锁定
                authorities     //权限集合，不能传入null
                );

    }




}
