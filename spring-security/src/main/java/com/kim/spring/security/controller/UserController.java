package com.kim.spring.security.controller;

import com.kim.spring.security.pojo.ResultVO;
import com.kim.spring.security.pojo.User;
import com.kim.spring.security.service.UserService;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
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
    //方法执行之前进行权限校验
    @PreAuthorize("hasAuthority('user:list')")
    public ResultVO list() {
        return ResultVO.success(userService.list());
    }

    @GetMapping("/ids")
    //方法执行之前进行权限校验,主要过滤掉集合参数的不符合条件的部分元素，以下过滤掉参数集合中不能被2整除的元素
    @PreFilter(filterTarget = "ids",value="filterObject%2 == 0")
    //方法执行之后进行权限校验，主要过滤掉返回值集合不符合条件的部分元素，以下过滤掉返回值集合中不能被4整除的元素
    @PostFilter("filterObject%4 == 0")
    public ResultVO listByIds(@RequestBody List<Integer> ids) {

        return ResultVO.success(ids);
    }



    @PostMapping("/add")
    //方法执行之前进行权限校验
    @PreAuthorize("hasRole('admin') or hasAuthority('user:add')")   //需要有admin的角色才能访问
    public ResultVO add(@RequestBody User user) {
        userService.add(user);
        return ResultVO.success();
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('user:update','user:list')") //有update或者list权限才能访问
    public ResultVO update(@RequestBody User user) {
        userService.update(user);
        return ResultVO.success();
    }

    @GetMapping("/get/{id}")
    //路径参数权限校验
    @PreAuthorize("hasAnyAuthority('user:list','user:get','user:update')")   //路径参数id小于10才能访问
    public ResultVO get(@PathVariable("id") Integer id) {

        return ResultVO.success(userService.get(id));
    }

    @DeleteMapping("/del/{id}")
    @PreAuthorize("#id > 1 and  hasAuthority('user:del')")
    public ResultVO del(@PathVariable("id") Integer id) {

        userService.del(id);
        return ResultVO.success();

    }

    //获取当前登录用户
    @GetMapping("/current")
    //方法执行之后进行权限校验，适合对返回值进行权限校验
    @PostAuthorize("returnObject.data.name== authentication.principal.username") //返回值等于当前用户才能访问
    public ResultVO getCurrent() {
        return ResultVO.success(userService.getCurrent());
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
