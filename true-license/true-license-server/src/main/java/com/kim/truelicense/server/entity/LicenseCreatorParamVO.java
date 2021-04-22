package com.kim.truelicense.server.entity;

import com.kim.truelicense.common.LicenseExtraModel;
import lombok.Data;

import java.util.Date;

/**
 * @author huangjie
 * @description  为用户生成证书需要的具体参数
 * @date 2019/12/2
 */
@Data
public class LicenseCreatorParamVO {

    /**有效期截至时间*/
    private Date expireTime;

    /**客户名称*/
    private String customerName;

    /**公钥钥库密码库，必须包含数字和字母*/
    private String storePass;

    /**私钥密码,必须包含数字和字母*/
    private String keyPass;

    /**描述信息*/
    private String description;

    /**额外的服务器硬件校验信息*/
    private LicenseExtraModel licenseExtraModel;






}
