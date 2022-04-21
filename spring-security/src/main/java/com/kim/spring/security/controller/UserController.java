package com.kim.spring.security.controller;

import com.kim.spring.security.pojo.User;
import com.kim.spring.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author huangjie
 * @description
 * @date 2022/4/20
 */
@RestController
@RequestMapping("/security/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/list")
    public List<User> list() {


        return userService.list();
    }

    @PostMapping("/add")
    public String add(@RequestBody User user) {
        userService.add(user);
        return "success";
    }

    @PutMapping("/update")
    public String update(@RequestBody User user) {

        return add(user);
    }

    @GetMapping("/get/{id}")
    public User get(@PathVariable("id") Integer id) {

        return userService.get(id);
    }

    @DeleteMapping("/del/{id}")
    public String del(@PathVariable("id") Integer id) {

        userService.del(id);
        return "success";

    }

    //获取当前登录用户
    @GetMapping("/current")
    public User getCurrent() {
        return userService.getCurrent();
    }

    //还有两种方式获取当前登录用户信息

    /**
     *      @GetMapping("/loginUser2")
     *     public UserDetails getCurrentUser(Authentication authentication) {
     *         UserDetails userDetails = (UserDetails) authentication.getPrincipal();
     *         return userDetails;
     *     }
     *
     *
     *      @GetMapping("/loginUser3")
     *      public UserDetails getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
     *          return userDetails;
     *      }
     */



}
