package com.kim.truelicense.common;

import lombok.Data;

/**
 * @author huangjie
 * @description   额外的服务器硬件校验信息对象,这里的属性可根据需求自定义
 * @date 2019/11/28
 */
@Data
public class LicenseExtraModel {

    /**客户机mac地址*/
    private String macAddress;

    /**客户机cpu核数*/
    private Integer cpuAmount;

    /**客户机gpu核数*/
    private Integer gpuAmount;

    /**
     * ram内存,单位：G
     * */
    private Integer ram;



}
