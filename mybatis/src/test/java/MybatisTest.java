import com.kim.mybatis.mapper.UserMapper;
import com.kim.mybatis.pojo.User;
import com.mysql.cj.xdevapi.SessionFactory;
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
import java.util.Date;
import java.util.List;

/**
 * @author huangjie
 * @description
 * @date 2021/11/1
 */
public class MybatisTest {

    private SqlSession sqlSession;
    private SqlSessionFactory sqlSessionFactory;
    private static final String STATEMENT="com.kim.mybatis.mapper.UserMapper";

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
    @DisplayName("新增")
    public void insert(){
        for(int i=0;i<20;i++){
            User user=User.builder().name("mike"+i).password("123"+i).createdAt(new Date()).build();
            int insert = sqlSession.insert(STATEMENT+".addUser", user);
            System.out.println(user.getId());
        }
        sqlSession.commit();

    }

    @Test
    @DisplayName("修改")
    public void update(){
        User user=User.builder().id(2).name("lucy").password("321").build();
        int update = sqlSession.update(STATEMENT + ".updateUser",user);
        sqlSession.commit();
    }

    @Test
    @DisplayName("删除")
    public void delete(){
        int delete = sqlSession.delete(STATEMENT + ".deleteUser", 2);
        sqlSession.commit();
    }

    @Test
    @DisplayName("底层查询方法测试")
    public void test1() throws IOException {

        //通过mapper.xml的namespace.id执行mapper方法
        List<Object> users = sqlSession.selectList(STATEMENT+".findAll");
        users.stream().forEach(o -> {
            System.out.println(o);
        });
    }
    @Test
    @DisplayName("查询单个")
    public void selectOne(){
        Object o = sqlSession.selectOne(STATEMENT + ".findById", 3);
        User user=(User)o;
        System.out.println(user);
    }


    @Test
    @DisplayName("使用代理类方式")
    public void getMapper(){
        /**
         * 要求mapper.xml的namespace和mapper.java的全路径名一样，id=方法名
         * 通过namespace.id定位到mapperStatement，
         */
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.findAll();
        users.stream().forEach(o -> {
            System.out.println(o);
        });

    }




}
