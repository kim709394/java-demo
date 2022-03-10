package com.kim.spring.mvc.controller;

import com.kim.spring.mvc.pojo.Order;
import org.springframework.web.bind.annotation.*;

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


}
