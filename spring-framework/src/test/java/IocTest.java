
import com.kim.springframework.SpringframeworkApplication;
import com.kim.springframework.ioc.annotation.bean.*;
import com.kim.springframework.ioc.postprocessor.MyBeanFactoryPostProcessor;
import com.kim.springframework.ioc.postprocessor.MyBeanPostProcessor;
import com.kim.springframework.ioc.xml.bean.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author huangjie
 * @description
 * @date 2021/12/8
 */
public class IocTest {


    @Test
    public void xmlTest(){
        /***通过硬盘路径获取ioc容器上下文
         * ApplicationContext applicationContext=new FileSystemXmlApplicationContext("c:/application-context.xml");
        */
        //通过类路径获取ioc容器上下文
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("application-context.xml");
        StudentBean student = applicationContext.getBean(StudentBean.class);
        TeacherBean teacher = applicationContext.getBean(TeacherBean.class);
        WorkersBean works = applicationContext.getBean(WorkersBean.class);
        SchoolBean school=applicationContext.getBean(SchoolBean.class);
        System.out.println(student);
        System.out.println(teacher);
        System.out.println(works);
        System.out.println(school);
        //销毁容器
        ((ClassPathXmlApplicationContext) applicationContext).close();
    }


    @Test
    public void annotationTest(){
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(SpringframeworkApplication.class);
        Car car = applicationContext.getBean(Car.class);
        Engine engine = applicationContext.getBean(Engine.class);
        Wheel wheel = applicationContext.getBean(Wheel.class);
        LifeCycleBean lifeCycleBean = applicationContext.getBean(LifeCycleBean.class);
        System.out.println(lifeCycleBean);
        System.out.println(car);
        System.out.println(engine);
        System.out.println(wheel);
        //销毁容器
        ((AnnotationConfigApplicationContext) applicationContext).close();
    }


    @Test
    public void factoryBeanTest(){

        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(SpringframeworkApplication.class);
        //通过类型获取
        Computer computer = applicationContext.getBean(Computer.class);
        ComputerFactoryBean computerFactoryBean = applicationContext.getBean(ComputerFactoryBean.class);

        System.out.println(computer);
        System.out.println(computerFactoryBean);

        //通过名称获取
        //直接通过名称获取的是工厂bean生产出来的bean
        computer = (Computer)applicationContext.getBean("computerFactoryBean");
        //前面加上一个&的符号通过名称获取的是工厂bean本身
        computerFactoryBean=(ComputerFactoryBean)applicationContext.getBean("&computerFactoryBean");
        System.out.println(computer);
        System.out.println(computerFactoryBean);

    }

    /**
     * init-method前后置处理器
     * */
    @Test
    public void beanPostProcessor(){
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(SpringframeworkApplication.class);
        applicationContext.getBean(MyBeanPostProcessor.class);

    }

    /**
     * BeanDefinition初始化完成时的后置处理器
     * */
    @Test
    public void beanFactoryPostProcessor(){
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(SpringframeworkApplication.class);
        applicationContext.getBean(MyBeanFactoryPostProcessor.class);
    }

    /**
     * 实现InitializingBean接口，在属性输入后执行业务方法
     * */
    @Test
    public void initializingBeanAfterPropertiesSet(){
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(SpringframeworkApplication.class);
        applicationContext.getBean(Wheel.class);
    }

    /**
     * bean的生命周期
     * */
    @Test
    public void beanLifeCycle(){
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(SpringframeworkApplication.class);
        applicationContext.getBean(LifeCycleBean.class);
        ((AnnotationConfigApplicationContext)applicationContext).close();
    }

    /**
     * 获取被某个注解注解上的bean
     * */
    @Test
    public void getBeanWithAnnotations(){
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(SpringframeworkApplication.class);
        //获取bean所在的类被@Service注解的所有bean
        Map<String, Object> service = applicationContext.getBeansWithAnnotation(Service.class);
        System.out.println(service);
    }


}
