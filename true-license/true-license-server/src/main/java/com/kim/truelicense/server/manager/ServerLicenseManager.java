package com.kim.truelicense.server.manager;

import de.schlichtherle.license.*;
import de.schlichtherle.xml.GenericCertificate;

import java.util.Date;

/**
 * @author huangjie
 * @description   服务端证书管理类(生成证书)
 * @date 2019/11/28
 */
public class ServerLicenseManager extends LicenseManager {


    public ServerLicenseManager(LicenseParam param) {
        super(param);
    }


    /**
     * 证书生成的验证
     */
    protected synchronized void validateCreate(final LicenseContent content) throws LicenseContentException {
        Date now = new Date();
        Date notBefore = content.getNotBefore();
        Date notAfter = content.getNotAfter();
        if (notBefore != null && now.before(notBefore)) {
            throw new LicenseContentException("证书尚未生效，无法生成");
        }
        if (notAfter != null && now.after(notAfter)) {
            throw new LicenseContentException("证书已过期，无法生成");
        }

        if (notBefore != null && notAfter != null && notBefore.after(notAfter)) {
            throw new LicenseContentException("证书生效时间晚于失效时间，无法生成");
        }
    }

    /**
     *重写生成证书的方法，增加生成参数验证
     * */
    @Override
    protected synchronized byte[] create(LicenseContent content, LicenseNotary notary) throws Exception {
        initialize(content);
        validateCreate(content);
        final GenericCertificate genericCertificate=notary.sign(content);
        return getPrivacyGuard().cert2key(genericCertificate);
    }
}
