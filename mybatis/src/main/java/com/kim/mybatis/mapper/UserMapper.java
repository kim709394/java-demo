package com.kim.mybatis.mapper;

import com.kim.mybatis.pojo.User;
import com.kim.mybatis.pojo.UserPageInput;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huangjie
 * @description
 * @date 2021/11/1
 */
public interface UserMapper {

    //新增
    int addUser(User user);

    //查询集合
    List<User> findAll();

    //修改
    int updateUser(User user);

    //删除
    int deleteUser(Integer id);

    //查询单个
    User findById(Integer id);

    //分页
    List<User> getPageRecordsByPageInput( UserPageInput pageInput);

    Integer getTotalByPageInput(UserPageInput pageInput);

    //条件集合查询
    List<User> getUsersByIds(List<Integer> ids);

}
