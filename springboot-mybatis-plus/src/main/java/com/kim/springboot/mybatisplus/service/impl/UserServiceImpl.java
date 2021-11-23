package com.kim.springboot.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kim.springboot.mybatisplus.entity.dos.UserDO;
import com.kim.springboot.mybatisplus.entity.vos.UserVO;
import com.kim.springboot.mybatisplus.mapper.UserMapper;
import com.kim.springboot.mybatisplus.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        UserDO userDO=new UserDO();
        userDO.setId(null);
        BeanUtils.copyProperties(entityVO,userDO);
        userDO.setCreatedAt(new Date());
        baseMapper.insert(userDO);
        return userDO.getId();
    }

    //修改
    @Override
    @Transactional
    public void update(UserVO entityVO){
        UserDO userDO=new UserDO();
        BeanUtils.copyProperties(entityVO,userDO);
        baseMapper.updateById(userDO);
    }

    //查看
    @Override
    @Transactional
    public UserVO get(Long id){
        UserDO userDO = baseMapper.selectById(id);
        UserVO userVO=new UserVO();
        BeanUtils.copyProperties(userDO,userVO);
        return userVO;
    }

    //列表
    @Override
    @Transactional
    public List<UserVO> listAll(){
        List<UserDO> userDOs = baseMapper.selectList(new LambdaQueryWrapper<UserDO>().orderByDesc(UserDO::getCreatedAt));
        return userDOs.stream().map(userDO -> {
            UserVO userVO=new UserVO();
            BeanUtils.copyProperties(userDO,userVO);
            return userVO;
        }).collect(Collectors.toList());

    }

}
