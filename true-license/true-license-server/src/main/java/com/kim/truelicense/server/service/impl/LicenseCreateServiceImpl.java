package com.kim.truelicense.server.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kim.truelicense.common.ConfigParam;
import com.kim.truelicense.common.CustomKeyStoreParam;
import com.kim.truelicense.common.LicenseExtraModel;
import com.kim.truelicense.server.entity.LicenseCreatorParam;
import com.kim.truelicense.server.entity.LicenseCreatorParamVO;
import com.kim.truelicense.server.exception.LicenseException;
import com.kim.truelicense.server.manager.ServerLicenseManager;
import com.kim.truelicense.server.service.LicenseCreateService;
import com.kim.truelicense.server.util.ExecCmdUtil;
import de.schlichtherle.license.*;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;

import javax.security.auth.x500.X500Principal;
import java.io.*;
import java.util.*;
import java.util.prefs.Preferences;

/**
 * @author huangjie
 * @description 证书生成接口实现类
 * @date 2019/11/29
 */

public class LicenseCreateServiceImpl implements LicenseCreateService {

    private static final X500Principal DEFAULT_HOLDER_ISSUER = new X500Principal("CN=localhost, OU=localhost, O=localhost, L=SH, ST=SH, C=CN");

    @Override
    public void generateLicense(LicenseCreatorParamVO paramVO) throws Exception {

        Map<String, Object> map = buildCreator(paramVO);
        LicenseCreatorParam param = (LicenseCreatorParam) map.get("creator");
        ZipFile clientZipFile = (ZipFile) map.get("clientZipFile");
        try {
            LicenseParam licenseParam = initLicenseParam(param);
            LicenseManager licenseManager = new ServerLicenseManager(licenseParam);
            LicenseContent licenseContent = initLcenseContent(param);
            licenseManager.store(licenseContent, new File(param.getLicensePath()));
            //log.info(param.getSubject() + "证书生成成功");
            clientZipFile.addFile(param.getLicensePath());
        } catch (Exception e) {
            e.printStackTrace();
            //log.error("生成证书失败:" + param.getSubject());
            throw new LicenseException("生成证书失败:" + param.getSubject());
        }

    }


    private String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 封装证书生成参数
     */
    private Map<String, Object> buildCreator(LicenseCreatorParamVO paramVO) throws Exception {
        String customerName = paramVO.getCustomerName();
        String privateAlias = customerName + "-private-alias";
        String publicAlias = customerName + "-public-alias";
        String relativePath = relativePath();
        String currentCustomerDir = relativePath + customerName + uuid() + "/";
        File file = new File(currentCustomerDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        String privateKeystore = currentCustomerDir + "privateKeys.keystore";
        String publicKeystore = currentCustomerDir + "publicCerts.keystore";

        LicenseCreatorParam param = new LicenseCreatorParam();
        param.setSubject(customerName);
        param.setPrivateAlias(privateAlias);
        param.setKeyPass(paramVO.getKeyPass());
        param.setStorePass(paramVO.getStorePass());
        param.setLicensePath(currentCustomerDir + "license.lic");
        param.setPrivateKeysStorePath(privateKeystore);
        param.setExpiryTime(paramVO.getExpireTime());
        param.setDescription(paramVO.getDescription());
        param.setLicenseExtraModel(paramVO.getLicenseExtraModel());

        //生成私钥库
        String exe1 = "keytool -genkeypair -keysize 1024 -validity " + getValidity(param.getIssuedTime(),paramVO.getExpireTime()) + " -alias " + privateAlias + " -keystore " + privateKeystore + " -storepass " + paramVO.getStorePass() + " -keypass " + paramVO.getKeyPass() + " -dname \"CN=localhost, OU=localhost, " + "O=localhost, L=SH, ST=SH, C=CN\"";

        String exe2 = "keytool -exportcert -alias " + privateAlias + " -keystore " + privateKeystore + " -storepass " + paramVO.getStorePass() + " -file \"" + currentCustomerDir + "certfile.cer\"";

        String exe3 = "keytool -noprompt -import -alias " + publicAlias + " -file \"" + currentCustomerDir + "certfile.cer\" -keystore " + publicKeystore + " -storepass " + paramVO.getStorePass();
        ExecCmdUtil.exec(exe1);
        ExecCmdUtil.exec(exe2);
        ExecCmdUtil.exec(exe3);
        ZipFile clientZipFile = generateClientConfig(param, currentCustomerDir, publicAlias);
        Map<String, Object> map = new HashMap<>();
        map.put("creator", param);
        map.put("clientZipFile", clientZipFile);
        return map;
    }

    private ZipFile generateClientConfig(LicenseCreatorParam param, String currentCustomerDir, String publicAlias) throws Exception {

        ZipFile clientLicense = new ZipFile(currentCustomerDir + "clientLicense.zip");
        File config = new File(currentCustomerDir + "clientConfig.json");
        ConfigParam configParam = new ConfigParam();
        configParam.setPublicAlias(publicAlias);
        configParam.setStorePass(param.getStorePass());
        configParam.setSubject(param.getSubject());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(configParam);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(config);
            out.write(json.getBytes("UTF-8"));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new LicenseException("密钥文件生成失败");
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        List<File> files = new ArrayList<>();
        files.add(config);
        files.add(new File(currentCustomerDir + "publicCerts.keystore"));
        clientLicense.addFiles(files);
        return clientLicense;
    }
    //将有效时间转换成天
    private int getValidity(Date issuedTime, Date expireTime) {
        long issued = issuedTime.getTime();
        long expire = expireTime.getTime();
        long differ = expire - issued;
        long remaining = differ % (24L * 3600L * 1000L);
        Long validity = differ / (24L * 3600L * 1000L);
        if(remaining>0){
            validity++;
        }
        return validity.intValue();
    }

    private boolean isWindows() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            return true;
        }
        return false;
    }

    //证书生成路径
    private String relativePath() {
        if (isWindows()) {
            return "d:/license/";
        }
        return "/data/license/";
    }

    /**
     * 设置证书生成参数
     */
    private LicenseParam initLicenseParam(LicenseCreatorParam param) {
        Preferences preferences = Preferences.userNodeForPackage(LicenseCreateServiceImpl.class);
        //设置密钥
        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());
        KeyStoreParam privateStoreParam = new CustomKeyStoreParam(LicenseCreateServiceImpl.class, param.getPrivateKeysStorePath(), param.getPrivateAlias(), param.getStorePass(), param.getKeyPass());
        LicenseParam licenseParam = new DefaultLicenseParam(param.getSubject(), preferences, privateStoreParam, cipherParam);
        return licenseParam;

    }

    /**
     * 设置证书生成内容
     */
    private LicenseContent initLcenseContent(LicenseCreatorParam param) {

        LicenseContent licenseContent = new LicenseContent();
        licenseContent.setHolder(DEFAULT_HOLDER_ISSUER);
        licenseContent.setIssuer(DEFAULT_HOLDER_ISSUER);
        licenseContent.setSubject(param.getSubject());
        licenseContent.setIssued(param.getIssuedTime());
        licenseContent.setNotBefore(param.getIssuedTime());
        licenseContent.setNotAfter(param.getExpiryTime());
        licenseContent.setConsumerType(param.getConsumerType());
        licenseContent.setConsumerAmount(param.getConsumerAmount());
        licenseContent.setInfo(param.getDescription());

        if (param != null && param.getLicenseExtraModel() != null) {
            licenseContent.setExtra(param.getLicenseExtraModel());
        }

        return licenseContent;
    }


}
