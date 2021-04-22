package com.kim.truelicense.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @Author kim
 * @Since 2021/4/22
 * 应用启动时校验证书是否安装成功
 */
@SpringBootApplication
public class TrueLicenseClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrueLicenseClientApplication.class);
    }

    @Bean
    public LicenseInstallerBean licenseInstallerBean() throws Exception {
        //模拟获取客户机的校验参数信息,与证书生成时设置的参数需一致，否则无法安装证书
        ClientMachineParam clientMachineParam=new ClientMachineParam();
        clientMachineParam.setCpuAmount(16);
        clientMachineParam.setGpuAmount(16);
        clientMachineParam.setMacAddress("2c:4d:54:65:d2:12");
        clientMachineParam.setRam(16);
        LicenseInstallerBean licenseInstallerBean = new LicenseInstallerBean();
        //安装证书，即校验客户机器参数是否符合证书要求，符合则安装成功，不符合则报错无法启动。
        licenseInstallerBean.installLicense(clientMachineParam);
        return licenseInstallerBean;
    }




}
