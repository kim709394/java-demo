package com.kim.truelicense.client;


import lombok.Data;

/**
 * @author huangjie
 * @description  模拟获取客户端服务器参数类
 * @date 2019/11/29
 */
@Data
public class ClientMachineParam {

    /**mac地址*/
    private String macAddress="2c:4d:54:65:d2:12";

    /**cpu核数*/
    private Integer cpuAmount=16;

    /**gpu核数*/
    private Integer gpuAmount=16;

    /**ram内存*/
    private Integer ram=16;

}
