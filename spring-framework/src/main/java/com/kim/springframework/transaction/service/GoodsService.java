package com.kim.springframework.transaction.service;

import com.kim.springframework.transaction.Goods;

import java.util.List;

/**
 * @author huangjie
 * @description
 * @date 2022/1/18
 */
public interface GoodsService {

    //新增
    void insert(Goods goods);

    //新增抛出异常
    void insertWithException(Goods goods);


    //查询
    List<Goods> findAll();
}
