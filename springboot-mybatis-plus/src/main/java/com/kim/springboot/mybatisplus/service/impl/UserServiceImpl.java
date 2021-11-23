package com.kim.springboot.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kim.springboot.mybatisplus.entity.dos.UserDO;
import com.kim.springboot.mybatisplus.entity.vos.UserVO;
import com.kim.springboot.mybatisplus.mapper.UserMapper;
import com.kim.springboot.mybatisplus.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author kim
 * @since 2021-11-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements IUserService {


    //新增
    @Override
    @Transactional
    public Long add(UserVO entityVO){

        return 0;
    }

    //修改
    @Override
    @Transactional
    public void update(UserVO entityVO){

    }

    //查看
    @Override
    @Transactional
    public UserVO get(Long id){

        return null;
    }

    //列表
    @Override
    @Transactional
    public List<UserVO> listAll(){
        return null;
    }

}
