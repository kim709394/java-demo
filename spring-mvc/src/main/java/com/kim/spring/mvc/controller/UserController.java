package com.kim.spring.mvc.controller;

import com.kim.spring.mvc.pojo.User;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huangjie
 * @description 前后端一体控制器，转发到视图页面
 * @date 2022/3/10
 */
@Controller
@RequestMapping("/user")
public class UserController {


    @RequestMapping(method = RequestMethod.POST,value = "/list")
    public String userList(Model model){
        List<User> users=new ArrayList<>();
        User u1=new User();
        u1.setId(1);
        u1.setName("lucy");
        u1.setAge(18);
        u1.setCreateTime(new Date());
        User u2=new User();
        u2.setId(2);
        u2.setName("mike");
        u2.setAge(23);
        u2.setCreateTime(new Date());
        users.add(u1);
        users.add(u2);
        model.addAttribute("users",users);
        return "userlist";
    }

    //@ResponseBody注解，自动将返回值转化为json字符串返回
    @RequestMapping(method = RequestMethod.GET,value = "/get")
    @ResponseBody
    public User get(@RequestParam(value="id") Integer id){
        User user=new User();
        user.setId(id);
        user.setName("john");
        user.setAge(28);
        user.setCreateTime(new Date());
        return user;
    }

    //表单请求，前端请求参数的字段名和该方法的参数对象的属性一一对应
    @RequestMapping(method = RequestMethod.POST,value = "/add",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public User add(User user){

        return user;
    }

}
