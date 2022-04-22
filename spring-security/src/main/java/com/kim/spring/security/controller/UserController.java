package com.kim.spring.security.controller;

import com.kim.spring.security.pojo.User;
import com.kim.spring.security.service.UserService;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

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



    //获取验证码
    @GetMapping("/verify/code")
    public void verifyCode(HttpServletRequest request,HttpServletResponse response){
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        // 设置字体
        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置
        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);

        // 验证码存入缓存
        String key = request.getRemoteAddr();
        userService.putVerifyCode(key,specCaptcha.text().toLowerCase());
        // 输出图片流
        try (ServletOutputStream out = response.getOutputStream()){
            specCaptcha.out(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
