package com.kim.spring.security.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author huangjie
 * @description
 * @date 2022/4/20
 */
@Controller
@RequestMapping("/security/front")
public class FrontController {


    //登录页面
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    //首页
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    //用户列表
    @GetMapping("/user")
    public String user(){
        return "userlist";
    }

}
