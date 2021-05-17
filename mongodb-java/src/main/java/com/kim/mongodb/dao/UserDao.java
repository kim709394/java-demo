package com.kim.mongodb.dao;

import com.kim.mongodb.entity.UserDO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author huangjie
 * @description
 * @date 2021-5-5
 */
@Repository
public interface UserDao extends MongoRepository<UserDO,String> {


}
