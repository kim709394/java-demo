package com.kim.truelicense.server;

import com.kim.truelicense.common.LicenseExtraModel;
import com.kim.truelicense.server.entity.LicenseCreatorParamVO;
import com.kim.truelicense.server.service.LicenseCreateService;
import com.kim.truelicense.server.service.impl.LicenseCreateServiceImpl;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * @author huangjie
 * @description   证书生成
 * @date 2021-4-21
 */
public class GenerateLicenseTest {

    @Test
    public void generateLicense() throws Exception {
        LicenseCreateService licenseCreateService=new LicenseCreateServiceImpl();
        //设置证书校验参数
        LicenseCreatorParamVO paramVO=new LicenseCreatorParamVO();
        paramVO.setCustomerName("阿里巴巴");
        paramVO.setDescription("马云爸爸");
        paramVO.setKeyPass("123456a");
        paramVO.setStorePass("123456a");
        //设置过期时间
        Calendar calendar=Calendar.getInstance();
        long expire=new Date().getTime()+(24L*3600L*1000L);
        calendar.setTimeInMillis(expire);
        paramVO.setExpireTime(calendar.getTime());
        //设置额外校验参数
        LicenseExtraModel extraModel=new LicenseExtraModel();
        extraModel.setRam(16);
        extraModel.setCpuAmount(16);
        extraModel.setGpuAmount(16);
        extraModel.setMacAddress("2c:4d:54:65:d2:12");
        paramVO.setLicenseExtraModel(extraModel);
        licenseCreateService.generateLicense(paramVO);
    }

}
