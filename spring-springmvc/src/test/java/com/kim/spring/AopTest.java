package com.kim.spring;

import com.kim.spring.entity.UserDO;
import com.kim.spring.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author kim
 * aop测试
 * @Since 2021/5/28
 */
@SpringBootTest(classes = SpringSpringMVCApplication.class)
@RunWith(value= SpringJUnit4ClassRunner.class)
public class AopTest {

    @Autowired
    private UserService userService;

    /**
     * 测试前置增强
     * */
    @Test
    public void testBefore(){
        UserDO userDO = userService.before("mike");
        System.out.println(userDO);
    }

    /**
     * 测试后置增强
     * 不管方法是否抛异常都执行
     * */
    @Test
    public void testAfter(){
        UserDO userDO = userService.after("mike");
        System.out.println(userDO);
    }

    /**
     * 测试后置增强
     * 方法出异常将不执行
     * */
    @Test
    public void testAfterReturning(){
        UserDO userDO = userService.afterReturning("mike");
        System.out.println(userDO);
    }

    /**
     * 测试环绕增强
     * */
    @Test
    public void testAround(){
        UserDO userDO = userService.around("mike");
        System.out.println("最终返回值:"+userDO);
    }


    /**
     * 测试异常增强
     * */
    @Test
    public void testAfterThrowing(){
        UserDO userDO = userService.afterThrowing("mike");
        System.out.println(userDO);
    }
}
