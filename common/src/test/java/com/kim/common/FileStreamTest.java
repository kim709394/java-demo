package com.kim.common;

import com.kim.common.consts.FileType;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author kim
 * @Since 2021/4/21
 * 文件流操作
 */
public class FileStreamTest {


    @Test
    @DisplayName("获取类路径下的文件")
    public void getClassPathResources() throws IOException {
        //java原生方式获取
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("img/test1.jpg");
        Enumeration<URL> resources = this.getClass().getClassLoader().getResources("img/test1.jpg");
        File file=null;
        UrlResource resource=null;
        while(resources.hasMoreElements()){
            URL url = resources.nextElement();
            resource = new UrlResource(url);
        }
        if(System.getProperty("os.name").indexOf("Windows")!=-1){
            //windows下可以直接获取文件
            file=resource.getFile();
        }else{
            //linux下只能获取流，要获取文件可以写到一个临时文件
            InputStream inputStream = resource.getInputStream();
        }
        //spring工具类提供
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] res = resourcePatternResolver.getResources(
                ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/img/test1.jpg");
        for(Resource r: res){
            InputStream in = r.getInputStream();
        }
    }

    @Test
    @DisplayName("获取项目根目录下的文件")
    public void readRootFile() throws Exception {
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
        InputStream rootStream = new FileInputStream(projectPath+"\\root.xml");
        String path = this.getClass().getResource("/").getPath();
        //获取类路径绝对路径
        System.out.println(path);
        SAXReader reader=new SAXReader();
        Document document = reader.read(rootStream);
        String field = document.getRootElement().element("configuration").attributeValue("field");
        System.out.println(field);
    }

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
