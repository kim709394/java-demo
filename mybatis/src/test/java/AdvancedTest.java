import com.kim.mybatis.mapper.OrderMapper;
import com.kim.mybatis.mapper.UserMapper;
import com.kim.mybatis.pojo.Order;
import com.kim.mybatis.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author huangjie
 * @description  高级应用
 * @date 2021/11/4
 */
public class AdvancedTest {

    private SqlSession sqlSession;
    private SqlSessionFactory sqlSessionFactory;

    @BeforeEach
    public void readConfig() throws IOException {

        InputStream resource= Resources.getResourceAsStream("mybatis-cfg.xml");
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(resource);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        this.sqlSession = sqlSession;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @AfterEach
    public void close(){
        sqlSession.close();
    }



    @Test
    @DisplayName("一级缓存")
    public void firstLevelCache(){
        /**
         * 同一个sqlSession的查询将会被一级缓存缓存起来，当数据被修改后，缓存被清空
         * sqlSession通常和ThreadLocal绑定，一个线程开启一个sqlSession绑定，用完后要close
         * 一级缓存默认是开启的
         * */
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession1.getMapper(UserMapper.class);
        System.out.println("--------第一次查询------");
        User user1 = mapper.findById(3);
        System.out.println("--------第二次查询-------");
        User user2 = mapper.findById(3);
        System.out.println(user1 == user2);
        System.out.println("-------修改数据后，第三次查询--------");
        user2.setName("john");
        //修改数据后，便清空了缓存
        mapper.updateUser(user2);
        User user3 = mapper.findById(3);
        System.out.println(user3 == user2);
    }


    @Test
    @DisplayName("二级缓存")
    public void secondLevelCache(){
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        SqlSession sqlSession3 = sqlSessionFactory.openSession();
        UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
        UserMapper mapper3 = sqlSession3.getMapper(UserMapper.class);
        System.out.println("******************sqlSession1查询id为3的用户信息*****************");
        User user = mapper1.findById(3);
        System.out.println(user);
        sqlSession1.close();
        System.out.println("******************sqlSession3第一次查询id为3的用户信息*****************");
        User user2 = mapper3.findById(3);
        System.out.println(user == user2);
        System.out.println(user2);
        System.out.println("******************sqlSession2修改id为3的用户信息*****************");
        user.setName("john");
        mapper2.updateUser(user);
        sqlSession2.commit();
        sqlSession2.close();
        System.out.println("******************sqlSession3第二次查询id为3的用户信息*****************");
        User user3 = mapper3.findById(3);
        sqlSession3.close();
        System.out.println(user == user3);
        System.out.println(user3);
        sqlSession3.close();
    }


    @Test
    @DisplayName("延迟加载")
    public void lazyLoad(){

        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        /**延迟加载需要开启全局配置和在具体方法上配置延迟加载策略
         *返回的是一个默认使用javassist动态代理的代理类
         *第一次查询的时候没有查询延迟加载的对象，当对象调用延迟加载的对象的get方法时，在通过事先
         * 保存好的sql进行查询并返回
         */
        Order order = mapper.oneToMany(1);
        order.getGoodsList().stream().forEach(goods -> {
            System.out.println(goods);
        });


    }

}
