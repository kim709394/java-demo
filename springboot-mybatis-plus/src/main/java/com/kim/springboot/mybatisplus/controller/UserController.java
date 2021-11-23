package com.kim.springboot.mybatisplus.controller;

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
    public ResultVO add(@RequestBody ){

        return ResultVO.success();
    }

    @ApiOperation(value = "修改")
    @PutMapping("/update")
    public ResultVO update(@RequestBody ){

        return ResultVO.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResultVO delete(@PathVariable("id") Long id){

        return ResultVO.success();
    }

    @ApiOperation(value = "获取单个")
    @GetMapping("/get/{id}")
    public ResultVO get(@PathVariable("id") Long id){

        return ResultVO.success();
    }

    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public ResultVO list(){

        return ResultVO.success();
    }
}

