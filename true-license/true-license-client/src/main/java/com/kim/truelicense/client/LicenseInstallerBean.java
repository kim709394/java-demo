package com.kim.truelicense.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kim.truelicense.common.ConfigParam;
import com.kim.truelicense.common.CustomKeyStoreParam;
import de.schlichtherle.license.*;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.*;
import java.util.prefs.Preferences;

/**
 * @author huangjie
 * @description 证书安装业务类
 * @date 2019/12/4
 */
public class LicenseInstallerBean {


    private LicenseManager licenseManager;


    //安装证书
    public void installLicense(ClientMachineParam clientMachineParam) throws Exception {

        try {
            //解压证书和配置文件等
            extractZip();
            //获取配置文件
            ConfigParam configParam = getConfigParam();
            //安装证书
            Preferences preferences = Preferences.userNodeForPackage(LicenseInstallerBean.class);
            CipherParam cipherParam = new DefaultCipherParam(configParam.getStorePass());
            KeyStoreParam publicKeyStoreParam = new CustomKeyStoreParam(LicenseInstallerBean.class, getLicensePath() + "clientLicense/publicCerts.keystore", configParam.getPublicAlias(), configParam.getStorePass(), null);
            LicenseParam licenseParam = new DefaultLicenseParam(configParam.getSubject(), preferences, publicKeyStoreParam, cipherParam);
            licenseManager = new ClientLicenseManager(licenseParam,clientMachineParam);
            licenseManager.uninstall();
            LicenseContent licenseContent = licenseManager.install(new File(getLicensePath() + "clientLicense/license.lic"));
            //Log.info("证书认证通过，安装成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthorizedException("证书认证失败:" + e + " " + e.getMessage());
        }

    }

    //卸载证书
    public void uninstallLicense() throws Exception {
        if (licenseManager != null) {
            licenseManager.uninstall();
            //Log.info("证书已卸载");
        }
    }


    //即时验证证书合法性
    public void verify() throws Exception {
        if (licenseManager != null) {
            licenseManager.verify();
            //Log.info("证书认证通过");
        }
        throw new AuthorizedException("证书认证失败:licenseManager is null");
    }

    private void extractZip() throws ZipException {
        ZipFile config = new ZipFile(getLicensePath() + "clientLicense.zip");
        File licenseDir = new File(getLicensePath() + "clientLicense");
        if (!licenseDir.exists()) {
            licenseDir.mkdir();
        }
        config.extractAll(licenseDir.getAbsolutePath());
    }

    private ConfigParam getConfigParam() throws Exception {
        FileInputStream config = null;
        BufferedReader reader = null;
        try {
            config = new FileInputStream(getLicensePath() + "clientLicense/clientConfig.json");
            reader = new BufferedReader(new InputStreamReader(config, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                sb.append(temp);
            }
            ObjectMapper mapper = new ObjectMapper();
            ConfigParam configParam = mapper.readValue(sb.toString(), ConfigParam.class);
            return configParam;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (config != null) {
                config.close();
            }
        }
        return null;
    }

    private String getLicensePath() {

        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            return "d:/license/";
        }
        return "/data/license/";


    }


}
