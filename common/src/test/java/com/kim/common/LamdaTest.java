package com.kim.common;

import com.kim.common.service.LamService;
import org.junit.Test;

/**
 * @Author kim
 * @Since 2021/4/21
 * lamda表达式测试
 */
public class LamdaTest {

    @Test
    public void lamTest(){
        LamService lamService= lam->System.out.println(lam);
        lamService.lamdaExecute("myLam");
    }



}
