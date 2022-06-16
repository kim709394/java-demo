package com.kim.common.controller.son;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author kim
 * @Since 2021/4/21
 * 通过反射获取controller的注解信息
 */
@Api
@Controller
@RequestMapping("/reflect/son")
public class ReflectExampleSonController {

    @ApiOperation("测试接口")
    @GetMapping("/get")
    public String test(@RequestParam String param){

        return param;

    }


}
