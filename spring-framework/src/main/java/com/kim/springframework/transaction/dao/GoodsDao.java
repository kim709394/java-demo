package com.kim.springframework.transaction.dao;

import com.kim.springframework.transaction.Goods;

import java.util.List;

/**
 * @author huangjie
 * @description
 * @date 2022/1/18
 */
public interface GoodsDao {

    //新增抛出异常
    void insertWithException(Goods goods);

    //新增正常
    void insert(Goods goods);

    //查询
    List<Goods> findAll();

}
