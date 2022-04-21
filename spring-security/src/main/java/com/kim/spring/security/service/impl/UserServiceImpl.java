package com.kim.spring.security.service.impl;

import com.kim.spring.security.pojo.User;
import com.kim.spring.security.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author huangjie
 * @description
 * @date 2022/4/21
 */
@Service
public class UserServiceImpl implements UserService {


    //模拟数据库
    private static Map<Integer,User> users ;

    public UserServiceImpl(){
        users=new HashMap<>();
        //设置管理员
        User user=new User();
        user.setId(1);
        user.setName("admin");
        user.setAge(100);
        user.setPassword(bcryptPassword("123456"));
        user.setCreatedAt(new Date());
        users.put(1,user);
    }

    //使用框架自带的bcrypt算法进行密码加密
    private String bcryptPassword(String password){

        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    //使用框架自带的bcrypt算法进行密码的比对
    private boolean matchPassword(String password,String encodedPassword){

        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        return encoder.matches(password,encodedPassword);
    }

    @Override
    public Map<Integer, User> getUsers() {
        return users;
    }

    @Override
    public User getUserByName(String username) {
        return users.values().stream().filter(user -> {
            return user.getName().equals(username);
        }).findFirst().get();
    }

    public List<User> list(){

        return new ArrayList<>(users.values());
    }

    public void add( User user){
        users.put(user.getId(),user);
    }


    public void update(User user){

        add(user);
    }

    public User get(Integer id){

        return users.get(id);
    }

    public void del(Integer id){

        users.remove(id);

    }

    @Override
    public User getCurrent() {
        UserDetails userDetails= (UserDetails)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(userDetails != null){
            return getUserByName(userDetails.getUsername());
        }
        return null;
    }
}
