package com.kim.spring.mvc.controller;

import com.kim.spring.mvc.pojo.Order;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author huangjie
 * @description  前后的分离，后端接口控制器,crud
 * @date 2022/3/10
 */
//@RestController注解相当于在每个controller方法上加上了@ResponseBody注解
@RestController
@RequestMapping("/order")
public class OrderController {


    @PostMapping("/add")
    public Order add(@RequestBody Order order){
        return order;
    }

    //添加了@RequestParam注解的参数则该参数为必传
    @GetMapping("/get/id")
    public Order get(@RequestParam Integer id){
        Order order=new Order();
        order.setId(id);
        order.setName("订单1");
        order.setCreateTime(new Date());
        return order;
    }

    //Restfull风格get请求
    @GetMapping("/get/{name}")
    public Order getByName(@PathVariable(value="name") String name){
        Order order=new Order();
        order.setId(2);
        order.setName(name);
        order.setCreateTime(new Date());
        return order;
    }

    //对servlet原生api的支持
    @GetMapping("/servlet")
    public String servlet(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        System.out.println(request);
        System.out.println(response);
        System.out.println(session);
        return "success";
    }

    //全局异常捕捉
    @GetMapping("/exception")
    public String globalException(){
        int i=1/0;
        return "success";
    }


}
