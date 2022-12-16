package com.kim.spring.data.redis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author huangjie
 * @description
 * @date 2022-12-16
 */
@RestController
@RequestMapping("/session/redis")
public class RedisController {



    @GetMapping("/set")
    public String setSessionRedisDemo(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.setAttribute("redisSession","redisSession");
        session.setMaxInactiveInterval(60);
        return "success";
    }

    @GetMapping("/get")
    public String getSessionRedisDemo(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        if(session == null || session.getAttribute("redisSession") ==null){
            return "session invalidate";
        }else{
            Object redisSession = session.getAttribute("redisSession");
            System.out.println(redisSession.toString());
            return "success";
        }

    }

    @GetMapping("/del")
    public String delSession(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        if(session != null){
            session.invalidate();
        }
        return "success";
    }


}
