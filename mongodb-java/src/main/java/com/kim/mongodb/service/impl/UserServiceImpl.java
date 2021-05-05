package com.kim.mongodb.service.impl;

import com.kim.mongodb.dao.UserDao;
import com.kim.mongodb.entity.UserDO;
import com.kim.mongodb.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huangjie
 * @description
 * @date 2021-5-5
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;  //一般crud接口

    @Override
    public String add(UserDO userDO) {
        userDO.setId(null);
        userDao.insert(userDO);
        //userDao.save(userDO); id无值则新增， id有值则修改，如有空属性将会被覆盖
        return userDO.getId();
    }

    @Override
    public void update(UserDO userDO) {
        userDao.save(userDO);
    }

    @Override
    public void delete(String id) {
        userDao.deleteById(id);
    }

    @Override
    public void deleteByLogic(String id) {
        UserDO user = userDao.findById(id).get();
        user.setDeleted(true);
        userDao.save(user);
    }

    @Override
    public UserDO get(String id) {
        return userDao.findById(id).get();
    }

    @Override
    public List<UserDO> list() {
        return userDao.findAll(Sort.by(Sort.Direction.DESC,"createdTime"));
    }


    @Override
    public List<UserDO> examleLists() {
        Example<UserDO> example=new Example<UserDO>() {
            @Override
            public UserDO getProbe() {
                UserDO userDO=new UserDO();
                //设定查询条件
                if(userDO.getAge()!= null){
                   userDO.setAge(18);
                }
                if(StringUtils.isNotEmpty(userDO.getName())){
                    userDO.setName("mike");
                }
                return userDO;
            }

            @Override
            public ExampleMatcher getMatcher() {
                return ExampleMatcher.matching()
                        .withIgnoreNullValues()    //忽略空字段
                        .withMatcher("name",ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.REGEX));   //对指定字段指定匹配规则为模糊查询
            }
        };

        return userDao.findAll(example, Sort.by("createdTime").descending());   //倒序排序
    }
}
