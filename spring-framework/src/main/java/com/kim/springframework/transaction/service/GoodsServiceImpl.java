package com.kim.springframework.transaction.service;

import com.kim.springframework.transaction.Goods;
import com.kim.springframework.transaction.dao.GoodsDao;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author huangjie
 * @description
 * @date 2022/1/18
 */
@Service
@Data
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Override
    //配置事务传播为加入事务，事务隔离级别为可重复读，事务管理器bean名为transactionManager
    //抛出RuntimeException才回滚
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,
    transactionManager = "transactionManager",rollbackFor = RuntimeException.class)
    public void insert(Goods goods) {
        goodsDao.insert(goods);

    }

    @Override
    @Transactional
    public void insertWithException(Goods goods) {
        goodsDao.insertWithException(goods);
    }

    @Override
    @Transactional
    public List<Goods> findAll() {
        return goodsDao.findAll();
    }
}
