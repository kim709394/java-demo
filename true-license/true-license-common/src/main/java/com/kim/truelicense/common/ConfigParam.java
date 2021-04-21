package com.kim.truelicense.common;

import lombok.Data;

/**
 * @author huangjie
 * @description
 * @date 2019/12/3
 */
@Data
public class ConfigParam {

    /**主题*/
    private String subject;

    /**公钥别称*/
    private String publicAlias;

    /**访问公钥库的密码*/
    private String storePass;







}
