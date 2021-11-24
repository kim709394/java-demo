package com.kim.springboot.mybatisplus.entity.vos;

import com.kim.springboot.mybatisplus.consts.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author kim
 * @since 2021-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="User对象", description="用户表")
public class UserVO {


    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(value = "json字段")
    private Map<String,Object> gids;

    @ApiModelProperty(value = "状态")
    private StatusEnum status;

}
