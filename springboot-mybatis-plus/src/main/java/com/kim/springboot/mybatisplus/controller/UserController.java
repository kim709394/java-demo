package com.kim.springboot.mybatisplus.controller;

import com.kim.springboot.mybatisplus.config.MpInterceptor;
import com.kim.springboot.mybatisplus.entity.vos.ResultVO;
import com.kim.springboot.mybatisplus.entity.vos.UserPageInputVO;
import com.kim.springboot.mybatisplus.entity.vos.UserVO;
import com.kim.springboot.mybatisplus.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author kim
 * @since 2021-11-23
 */
@Api(tags = "用户表")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService service;

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public ResultVO add(@RequestBody UserVO userVO){
        //模拟设置租户id
        MpInterceptor.MyTenantLine.setTenantId(1L);
        return ResultVO.success(service.add(userVO));
    }

    @ApiOperation(value = "修改")
    @PutMapping("/update")
    public ResultVO update(@RequestBody UserVO userVO){
        //模拟设置租户id
        MpInterceptor.MyTenantLine.setTenantId(1L);
        service.update(userVO);
        return ResultVO.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResultVO delete(@PathVariable("id") Long id){
        //模拟设置租户id
        MpInterceptor.MyTenantLine.setTenantId(1L);
        return ResultVO.success(service.removeById(id));
    }

    @ApiOperation(value = "获取单个")
    @GetMapping("/get/{id}")
    public ResultVO get(@PathVariable("id") Long id){
        //模拟设置租户id
        MpInterceptor.MyTenantLine.setTenantId(1L);
        return ResultVO.success(service.get(id));
    }

    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public ResultVO list(){
        //模拟设置租户id
        MpInterceptor.MyTenantLine.setTenantId(1L);
        return ResultVO.success(service.listAll());
    }

    @ApiOperation(value = "通用方法分页")
    @PostMapping("/dopage/default")
    public ResultVO doPageByDefault(@RequestBody UserPageInputVO inputVO){
        //模拟设置租户id
        MpInterceptor.MyTenantLine.setTenantId(1L);
        return ResultVO.success(service.doPageByDefault(inputVO));
    }

    @ApiOperation(value = "xml配置方法分页")
    @PostMapping("/dopage/xml")
    public ResultVO doPageByXml(@RequestBody UserPageInputVO inputVO){

        //模拟设置租户id
        MpInterceptor.MyTenantLine.setTenantId(1L);
        return ResultVO.success(service.doPageByXml(inputVO));
    }

}

