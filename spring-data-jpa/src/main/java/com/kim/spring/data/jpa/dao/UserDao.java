package com.kim.spring.data.jpa.dao;

import com.kim.spring.data.jpa.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author huangjie
 * @description dao接口继承JpaRepository获得内置的crud接口方法，
 * 继承JpaSpecificationExecutor获得复杂的查询、分页、排序等接口方法
 * @date 2022-03-16
 */
public interface UserDao extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    /**
     * 使用hql语句写接口
     */
    @Query(" from User where id=?1 and name=?2")
    List<User> selectByIdAndName(Integer id, String name);


    /**
     * 使用原生sql写接口
     * nativeQuery设置为true代表使用原生sql语句，默认是false使用hql语句
     */
    @Query(value = "select * from t_user where id=?1 and name=?2", nativeQuery = true)
    List<User> selectByNative(Integer id, String name);

    /**
     * 方法命名规则写接口
     * 以findBy开头
     * 紧跟着属性名
     * 后面跟着like则是模糊查询，否则是等值查询
     */
    List<User> findByIdAndNameLike(Integer id, String name);


}
