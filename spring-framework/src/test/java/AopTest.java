import com.kim.springframework.AopApplication;
import com.kim.springframework.IocApplication;
import com.kim.springframework.aop.BusinessHandler;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * @author huangjie
 * @description  aop测试用例
 * @date 2022-01-16
 */
public class AopTest {

    //业务方法没有抛出异常的五种增强
    @Test
    public void xmlTest(){

        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("application-context.xml");
        BusinessHandler handler=(BusinessHandler)applicationContext.getBean(BusinessHandler.class);
        handler.handle("kim");

    }

    //业务方法抛出异常的五种增强
    @Test
    public void withExceptionXmlTest(){

        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("application-context.xml");
        BusinessHandler handler=(BusinessHandler)applicationContext.getBean(BusinessHandler.class);
        handler.throwExceptionHandle("kim");

    }

    //业务方法没有抛出异常的五种增强
    @Test
    public void annotationTest(){

        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(AopApplication.class);
        //当aop和循环依赖在一起时，被aop代理的bean并没有注入该注入的依赖bean
        BusinessHandler handler=(BusinessHandler)applicationContext.getBean(BusinessHandler.class);
        handler.handle("kim");

    }

    //业务方法抛出异常的五种增强
    @Test
    public void withExceptionAnnotationTest(){
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(AopApplication.class);
        BusinessHandler handler=(BusinessHandler)applicationContext.getBean(BusinessHandler.class);
        handler.throwExceptionHandle("kim");

    }

}
