package com.kim.common;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.junit.Test;

/**
 * @Author kim
 * @Since 2021/4/21
 * zip压缩文件测试
 */
public class ZipTest {

    @Test
    public void compressFile() throws ZipException {
        ZipFile zip=new ZipFile("d:/testZip.zip");
        zip.addFile("d:/test3.jpg");
        System.out.println(zip.getFile().getName());

    }


}
