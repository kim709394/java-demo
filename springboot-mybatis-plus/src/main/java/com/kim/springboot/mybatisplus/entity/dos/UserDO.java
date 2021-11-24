package com.kim.springboot.mybatisplus.entity.dos;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import java.util.Map;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.kim.springboot.mybatisplus.consts.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName(value="t_user",autoResultMap = true)
@ApiModel(value="User对象", description="用户表")
public class UserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(value = "删除时间")
    @TableLogic
    private Date deletedAt;

    @ApiModelProperty(value = "json字段")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String,Object> gids;

    @ApiModelProperty(value = "状态")
    private StatusEnum status;

}
