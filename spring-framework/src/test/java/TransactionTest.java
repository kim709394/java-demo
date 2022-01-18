import com.kim.springframework.TransactionApplication;
import com.kim.springframework.transaction.Goods;
import com.kim.springframework.transaction.dao.GoodsDao;
import com.kim.springframework.transaction.service.GoodsService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.util.resources.cldr.wal.CalendarData_wal_ET;

import java.util.Date;
import java.util.List;

/**
 * @author huangjie
 * @description   声明式事务测试用例
 * @date 2022/1/18
 */
public class TransactionTest {



    //xml配置模式：正常新增
    @Test
    public void xmlTransactionalSuccess(){

        ApplicationContext app=new ClassPathXmlApplicationContext("application-context.xml");
        GoodsService goodsService = app.getBean(GoodsService.class);
        Goods goods=new Goods();
        goods.setName("手机");
        goods.setPrice(2022.01);
        goods.setCreatedAt(new Date());
        goods.setDeletedAt(new Date());
        goodsService.insert(goods);
        List<Goods> goodsList = goodsService.findAll();
        System.out.println(goodsList);
    }

    //xml配置模式：新增抛出异常，测试是否能回滚，验证事务是否有切入,
    //如果回滚了说明声明式事务配置成功，如果没回滚说明声明式事务没有生效
    @Test
    public void xmlTransactionalFailed(){

        ApplicationContext app=new ClassPathXmlApplicationContext("application-context.xml");
        GoodsService goodsService = app.getBean(GoodsService.class);
        Goods goods=new Goods();
        goods.setName("电脑");
        goods.setPrice(2022.01);
        goods.setCreatedAt(new Date());
        goods.setDeletedAt(new Date());
        goodsService.insertWithException(goods);
    }

    //xml模式：验证没有事务参与的方法，是否会有事务参与，即抛出异常是否会回滚
    @Test
    public void xmlNoTransaction(){

        ApplicationContext app=new ClassPathXmlApplicationContext("application-context.xml");
        GoodsDao goodsDao = app.getBean(GoodsDao.class);
        Goods goods=new Goods();
        goods.setName("比亚迪dmi");
        goods.setPrice(2022.01);
        goods.setCreatedAt(new Date());
        goods.setDeletedAt(new Date());
        goodsDao.insertWithException(goods);
    }


    //注解模式：正常新增
    @Test
    public void annotationTransactionalSuccess(){

        ApplicationContext app=new AnnotationConfigApplicationContext(TransactionApplication.class);
        GoodsService goodsService = app.getBean(GoodsService.class);
        Goods goods=new Goods();
        goods.setName("手机");
        goods.setPrice(2022.01);
        goods.setCreatedAt(new Date());
        goods.setDeletedAt(new Date());
        goodsService.insert(goods);
        List<Goods> goodsList = goodsService.findAll();
        System.out.println(goodsList);
    }

    //注解模式：新增抛出异常，测试是否能回滚，验证事务是否有切入,
    //如果回滚了说明声明式事务配置成功，如果没回滚说明声明式事务没有生效
    @Test
    public void annotationTransactionalFailed(){
        ApplicationContext app=new AnnotationConfigApplicationContext(TransactionApplication.class);
        GoodsService goodsService = app.getBean(GoodsService.class);
        Goods goods=new Goods();
        goods.setName("电脑");
        goods.setPrice(2022.01);
        goods.setCreatedAt(new Date());
        goods.setDeletedAt(new Date());
        goodsService.insertWithException(goods);
    }

    //注解模式：验证没有事务参与的方法，是否会有事务参与，即抛出异常是否会回滚
    @Test
    public void annotationNoTransaction(){
        ApplicationContext app=new AnnotationConfigApplicationContext(TransactionApplication.class);

        GoodsDao goodsDao = app.getBean(GoodsDao.class);
        Goods goods=new Goods();
        goods.setName("比亚迪dmi");
        goods.setPrice(2022.01);
        goods.setCreatedAt(new Date());
        goods.setDeletedAt(new Date());
        goodsDao.insertWithException(goods);
    }


}
