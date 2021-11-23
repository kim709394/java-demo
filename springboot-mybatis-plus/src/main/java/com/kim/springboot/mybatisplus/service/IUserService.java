package com.kim.springboot.mybatisplus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kim.springboot.mybatisplus.entity.dos.UserDO;
import com.kim.springboot.mybatisplus.entity.vos.UserVO;

import java.util.List;


/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author kim
 * @since 2021-11-23
 */
public interface IUserService extends IService<UserDO> {

    /**
    * 新增
    * @param entityVO
    * @return Long
    * */
    Long add(UserVO entityVO);

    /**
    * 修改
    * @param entityVO
    * */
    void update(UserVO entityVO);

    /**
    *  查询单个
    * @param id
    * @return UserVO
    * */
    UserVO get(Long id);

    /**
    * 列表
    * @return List<UserVO>
    * */
    List<UserVO> listAll();

}
