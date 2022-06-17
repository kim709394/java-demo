package com.kim.common.entity;

import org.jetbrains.annotations.NotNull;

/**
 * @author huangjie
 * @description 电脑实体类, 实现Comparable接口，定制两个对象之间的大小比较
 * @date 2022/6/17
 */
public class Computer implements Comparable<Computer> {

    private Integer size;

    public Computer() {

    }

    public Computer(Integer size) {
        this.size = size;
    }

    //两个对象之间的大小比较根据尺寸来对比
    @Override
    public int compareTo(@NotNull Computer o) {
        return this.size.compareTo(o.size);
    }

    @Override
    public String toString() {
        return "Computer{size:"+size+"}";
    }
}
