package com.kim.spring.security.controller.front;

import org.springframework.stereotype.Controller;
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
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

}
