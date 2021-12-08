import com.kim.springframework.bean.StudentBean;
import com.kim.springframework.bean.TeacherBean;
import com.kim.springframework.bean.WorkersBean;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author huangjie
 * @description
 * @date 2021/12/8
 */
public class IocTest {


    @Test
    public void createBeanTest(){
        /***通过硬盘路径获取ioc容器上下文
         * ApplicationContext applicationContext=new FileSystemXmlApplicationContext("c:/application-context.xml");
        */
        //通过类路径获取ioc容器上下文
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("application-context.xml");
        StudentBean student = applicationContext.getBean(StudentBean.class);
        TeacherBean teacher = applicationContext.getBean(TeacherBean.class);
        WorkersBean works = applicationContext.getBean(WorkersBean.class);
        System.out.println(student);
        System.out.println(teacher);
        System.out.println(works);

    }


}
