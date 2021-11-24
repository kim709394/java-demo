package com.kim.springboot.mybatisplus.entity.vos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author huangjie
 * @description
 * @date 2021/11/24
 */
@ApiModel(value = "分页输出对象")
@Data
public class PageOutputVO<T> {

    @ApiModelProperty(value = "总记录数")
    private Integer recordCount;
    @ApiModelProperty(value = "总页数")
    private Integer totals;
    @ApiModelProperty(value = "单页数据集合")
    private List<T> records;

}
