package com.kim.common.designmode.proxy;

import com.kim.common.designmode.pojo.Computer;

/**
 * @author huangjie
 * @description
 * @date 2021/10/29
 */
public class ComputerService {

    public Computer get(Integer id){
        System.out.println("目标方法执行");
        return Computer.builder().brand("品牌").cpu("英特尔8核处理器").memory("16G").name("dell笔记本电脑").size("15寸")
                .videoCard("集成显卡").id(id).build();
    }

}
