
import com.kim.springframework.SpringframeworkApplication;
import com.kim.springframework.ioc.annotation.bean.Car;
import com.kim.springframework.ioc.annotation.bean.Engine;
import com.kim.springframework.ioc.annotation.bean.Wheel;
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

}
