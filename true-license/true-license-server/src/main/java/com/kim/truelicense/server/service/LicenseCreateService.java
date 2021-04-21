package com.kim.truelicense.server.service;

import com.kim.truelicense.server.entity.LicenseCreatorParamVO;

/**
 * @author huangjie
 * @description  证书生成接口
 * @date 2019/11/29
 */
public interface LicenseCreateService {

    /**
     * 生成一个证书
     * */
    void generateLicense(LicenseCreatorParamVO paramVO) throws Exception;


}
