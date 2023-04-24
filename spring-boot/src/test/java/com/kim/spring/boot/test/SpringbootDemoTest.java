package com.kim.spring.boot.test;

import com.kim.spring.boot.SpringbootDemoApplication;
import com.kim.spring.boot.config.MyConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.util.List;

/**
 * @author huangjie
 * @description
 * @date 2022-03-20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootDemoApplication.class)
@Slf4j
public class SpringbootDemoTest {

    @Autowired
    private MyConfiguration configuration;


    @Test
    public void configurationTest(){
        System.out.println(configuration);
    }

    @Test
    @DisplayName("动态修改logback的日志存储路径")
    public void changeLogFile() throws Exception{



        for(int i=0; i<10 ; i++){
            log.info("日志打印在哪里？");
            Thread.sleep(10*1000L);
            SAXReader logXml=new SAXReader();
            String path = this.getClass().getClassLoader().getResource("log/logback-dev.xml").getPath();
            System.out.println(path);
            Document document = logXml.read(this.getClass().getClassLoader().getResourceAsStream("log/logback-dev.xml"));
            Element property = document.getRootElement().element("property");
            List<Attribute> attributes = property.attributes();
            int finalI = i;
            attributes.stream().forEach(attribute -> {
                if(attribute.getName().equals("value")){
                    attribute.setValue("C:/log/foo"+ finalI);
                }
            });
            XMLWriter writer=new XMLWriter(new FileOutputStream(path));
            writer.write(document);
            writer.close();
        }



    }

}
