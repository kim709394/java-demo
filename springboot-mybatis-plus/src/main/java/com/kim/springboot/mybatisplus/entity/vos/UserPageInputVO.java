package com.kim.springboot.mybatisplus.entity.vos;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author huangjie
 * @description
 * @date 2021/11/24
 */
@Api("用户分页输入对象")
@Data
public class UserPageInputVO {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "创建时间开始")
    private Date createdAtStart;

    @ApiModelProperty(value = "创建时间结束")
    private Date createdAtEnd;

    @ApiModelProperty(value = "gid集合")
    private List<Long> gids;

    @ApiModelProperty(value = "页码")
    protected Integer pageNo;

    @ApiModelProperty(value = "页面容量")
    protected Integer pageSize;


}
