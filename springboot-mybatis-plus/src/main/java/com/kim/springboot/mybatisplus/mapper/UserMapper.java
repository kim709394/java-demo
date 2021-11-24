package com.kim.springboot.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kim.springboot.mybatisplus.entity.dos.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kim.springboot.mybatisplus.entity.vos.UserPageInputVO;
import com.kim.springboot.mybatisplus.entity.vos.UserVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author kim
 * @since 2021-11-23
 */
public interface UserMapper extends BaseMapper<UserDO> {

    //分页
    IPage<UserVO> doPage(Page<?> page, @Param("userPageInputVO") UserPageInputVO userPageInputVO);

}
