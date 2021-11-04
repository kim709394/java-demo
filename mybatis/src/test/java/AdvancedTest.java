import com.kim.mybatis.mapper.UserMapper;
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
        mapper.updateUser(user2);
        User user3 = mapper.findById(3);
        System.out.println(user3 == user2);
    }




}
