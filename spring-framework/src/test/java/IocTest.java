
import com.kim.springframework.SpringframeworkApplication;
import com.kim.springframework.ioc.annotation.bean.*;
import com.kim.springframework.ioc.xml.bean.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

}
