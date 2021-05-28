package com.kim.spring.service;

import com.kim.spring.entity.UserDO;

/**
 * @Author kim
 * @Since 2021/5/27
 */
public interface UserService {

    /**
     * 前置增强
     * */
    UserDO before(String name);

    /**
     * 后置增强
     * 不管方法是否抛异常都执行
     */
    UserDO after(String name);

    /**
     *后置增强
     * 方法出异常将不执行
     */
    UserDO afterReturning(String name);

    /**
     * 环绕增强,方法执行前后执行，还可以控制方法是否执行
     *
     */
    UserDO around(String name);

    /**
     * 异常增强
     */
    UserDO afterThrowing(String name);



}
