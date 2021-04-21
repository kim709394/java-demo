package com.kim.truelicense.client;

import com.kim.truelicense.common.LicenseExtraModel;
import de.schlichtherle.license.*;
import de.schlichtherle.xml.GenericCertificate;

/**
 * @author huangjie
 * @description  客户端证书管理类(证书验证)
 * @date 2019/11/29
 */
public class ClientLicenseManager extends LicenseManager {


    public ClientLicenseManager(LicenseParam param,ClientMachineParam machineParam){
        super(param);
        this.machineParam=machineParam;
    }

    private ClientMachineParam machineParam;

    /**
     * 重写验证证书方法，添加自定义参数验证
     * */
    @Override
    protected synchronized void validate(LicenseContent content) throws LicenseContentException {
        //系统验证基本参数：生效时间、失效时间、公钥别名、公钥密码
        super.validate(content);
        //验证自定义参数
        Object o=content.getExtra();
        if(o!=null && machineParam!=null && o instanceof LicenseExtraModel){
            LicenseExtraModel extraModel=(LicenseExtraModel) o;
            if(machineParam.getRam()!=extraModel.getRam()){
                throw new LicenseContentException("RAM内存不匹配");
            }
            if(machineParam.getCpuAmount()!=extraModel.getCpuAmount()){
                throw new LicenseContentException("CPU核数不匹配");
            }
            if(machineParam.getGpuAmount()!=extraModel.getGpuAmount()){
                throw new LicenseContentException("GPU核数不匹配");
            }
            if(!machineParam.getMacAddress().equals(extraModel.getMacAddress())){
                throw new LicenseContentException("MAC地址不匹配");
            }
        }else{
            throw new LicenseContentException("证书无效");
        }
    }


    /**
     * 重写证书安装方法，主要是更改调用本类的验证方法
     * */
    @Override
    protected synchronized LicenseContent install(final byte[] key, LicenseNotary notary) throws Exception {

        final GenericCertificate certificate = getPrivacyGuard().key2cert(key);
        notary.verify(certificate);
        final LicenseContent content = (LicenseContent) certificate.getContent();
        this.validate(content);
        setLicenseKey(key);
        setCertificate(certificate);

        return content;
    }

    /**
     * 重写验证证书合法的方法，主要是更改调用本类的验证方法
     * */
    @Override
    protected synchronized LicenseContent verify(LicenseNotary notary) throws Exception {
        GenericCertificate certificate = getCertificate();
        if (null != certificate)
            return (LicenseContent) certificate.getContent();

        // Load license key from preferences,
        final byte[] key = getLicenseKey();
        if (null == key)
            throw new NoLicenseInstalledException(getLicenseParam().getSubject());
        certificate = getPrivacyGuard().key2cert(key);
        notary.verify(certificate);
        final LicenseContent content = (LicenseContent) certificate.getContent();
        this.validate(content);
        setCertificate(certificate);

        return content;
    }
}
