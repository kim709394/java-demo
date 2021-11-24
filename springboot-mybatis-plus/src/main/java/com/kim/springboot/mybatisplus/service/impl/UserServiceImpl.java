package com.kim.springboot.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kim.springboot.mybatisplus.entity.dos.UserDO;
import com.kim.springboot.mybatisplus.entity.vos.PageOutputVO;
import com.kim.springboot.mybatisplus.entity.vos.UserPageInputVO;
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

    @Override
    public PageOutputVO<UserVO> doPageByDefault(UserPageInputVO inputVO) {
        LambdaQueryWrapper<UserDO> queryWrapper=new LambdaQueryWrapper<>();
        if(inputVO.getName() != null && !inputVO.getName().equals("")){
            queryWrapper.like(UserDO::getName,inputVO.getName());
        }
        if(inputVO.getPassword()!= null && !inputVO.getPassword().equals("")){
            queryWrapper.eq(UserDO::getPassword,inputVO.getPassword());
        }
        if(inputVO.getCreatedAtStart()!= null){
            queryWrapper.ge(UserDO::getCreatedAt,inputVO.getCreatedAtStart());
        }
        if(inputVO.getCreatedAtEnd()!= null){
            queryWrapper.le(UserDO::getCreatedAt,inputVO.getCreatedAtEnd());
        }
        queryWrapper.orderByDesc(UserDO::getCreatedAt);
        IPage<UserDO> page=new Page<>();
        page.setSize(inputVO.getPageSize());
        page.setCurrent(inputVO.getPageNo());
        IPage<UserDO> ipage = this.page(page, queryWrapper);
        PageOutputVO<UserVO> pageOutputVO=new PageOutputVO<>();
        pageOutputVO.setRecordCount(Integer.valueOf(String.valueOf(ipage.getTotal())));
        List<UserDO> records = ipage.getRecords();
        List<UserVO> userVOs = records.stream().map(userDO -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(userDO, userVO);
            return userVO;
        }).collect(Collectors.toList());
        pageOutputVO.setRecords(userVOs);
        return pageOutputVO;
    }

    @Override
    public PageOutputVO<UserVO> doPageByXml(UserPageInputVO inputVO) {

        Page<?> page = new Page<>();
        page.setCurrent(inputVO.getPageNo());
        page.setSize(inputVO.getPageSize());

        IPage<UserVO> ipage = baseMapper.doPage(page, inputVO);
        PageOutputVO<UserVO> pageOutputVO = new PageOutputVO<>();

        pageOutputVO.setRecordCount(Integer.valueOf(String.valueOf(ipage.getTotal())));
        pageOutputVO.setRecords(ipage.getRecords());
        return pageOutputVO;
    }
}
