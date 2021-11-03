package com.kim.mybatis.mapper;

import com.kim.mybatis.pojo.Goods;

import java.util.List;

/**
 * @author huangjie
 * @description
 * @date 2021/11/3
 */
public interface GoodsMapper {

    //一对一查询
    Goods queryOneToOne(Integer id);

    //根据订单id查询商品集合
    List<Goods> queryGoodsByOid(Integer oid);
}
