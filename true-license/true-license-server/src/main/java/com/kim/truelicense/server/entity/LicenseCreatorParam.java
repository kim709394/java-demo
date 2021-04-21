package com.kim.truelicense.server.entity;
import com.kim.truelicense.common.LicenseExtraModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huangjie
 * @description   授权生成参数
 * @date 2019/11/28
 */
@Data
public class LicenseCreatorParam implements Serializable {


    private static final long serialVersionUID = -70765183646276811L;


    /**证书主题*/
    private String subject;

    /**私钥别称*/
    private String privateAlias;

    /**私钥密码*/
    private String keyPass;

    /**访问公钥库的密码*/
    private String storePass;

    /**证书生成路径*/
    private String licensePath;

    /**私钥库存储路径*/
    private String privateKeysStorePath;

    /**证书生效时间*/
    private Date issuedTime=new Date();

    /**
     *证书失效时间
     * */
    private Date expiryTime;

    /**
     * 用户类型
     * */
    private String consumerType="user";

    /**
     * 用户数量
     * */
    private Integer consumerAmount=1;

    /**描述信息*/
    private String description;

    /**额外的服务器硬件校验信息*/
    private LicenseExtraModel licenseExtraModel;

















}
