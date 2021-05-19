package com.kim.common.entity;

import lombok.Data;

/**
 * @Author kim
 * 实体A和B的父类
 * @Since 2021/5/19
 */
@Data
public class C {
    protected Integer age;

    public static C cp(C c) {
        if (c == null) {
            return null;
        }
        C c1 = new C();
        c.setAge(c.getAge());
        return c1;
    }
}
