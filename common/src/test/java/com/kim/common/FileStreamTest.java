package com.kim.common;

import com.kim.common.consts.FileType;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author kim
 * @Since 2021/4/21
 * 文件流操作
 */
public class FileStreamTest {

    /**
     * 文件流分段
     */
    @Test
    public void testStreamMemory() throws IOException {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("img/test1.jpg");
        byte[] b = FileCopyUtils.copyToByteArray(inputStream);
        Map<String, InputStream> fileSplit = new HashMap<>();
        int intervalSize = 10;
        if(b.length<intervalSize){
            System.out.println("文件不需要分割");
        }
        int splitCount = b.length % intervalSize == 0 ? b.length / intervalSize : b.length / intervalSize + 1;

        int beginIndex=0;
        int endIndex=-1;
        for(int i=0;i<splitCount;i++){
            beginIndex=endIndex+1;
            byte[] intervalByte;
            if(b.length-intervalSize*i<intervalSize){
                endIndex=b.length-1;
                intervalByte=new byte[b.length-intervalSize*i];
            }else{
                intervalByte=new byte[intervalSize];
                endIndex=beginIndex+(intervalSize-1);
            }
            for(int j=0;j<intervalByte.length;i++){
                intervalByte[j]=b[beginIndex+j];
            }
            fileSplit.put(i+"",new ByteArrayInputStream(intervalByte));
        }

    }

    /**
     * 通过读取文件头信息判断文件是什么文件类型
     * */
    @Test
    public void whichFileType() throws IOException {

        InputStream in=this.getClass().getClassLoader().getResourceAsStream("pdf/为什么不买iphone.pdf");
        byte[] b=FileCopyUtils.copyToByteArray(in);
        ByteArrayInputStream source=new ByteArrayInputStream(b);
        ByteArrayInputStream task=new ByteArrayInputStream(b);
        byte[] temp=new byte[8];
        task.read(temp,0,temp.length);
        StringBuilder builder = new StringBuilder();
        if (temp == null || temp.length <= 0) {
            System.out.println("不是pdf文件");
        }
        String hv;
        for (int i = 0; i < temp.length; i++) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(temp[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        String head=builder.toString();
        FileType type=null;
        for (FileType fileType:FileType.values()
        ) {
            String fileHead = fileType.fileHead;
            if(fileHead.contains(head)||head.contains(fileHead)){
                type=fileType;
                break;
            }
        }
        if(null!=type){
            System.out.println(type.fileType);
        }

    }


}
