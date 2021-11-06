import com.kim.mybatis.mapper.GoodsMapper;
import com.kim.mybatis.mapper.OrderMapper;
import com.kim.mybatis.mapper.UserMapper;
import com.kim.mybatis.pojo.*;
import com.mysql.cj.xdevapi.SessionFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.management.DynamicMBean;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huangjie
 * @description
 * @date 2021/11/1
 */
public class ExecuteTest {

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
            User user=new User();
            user.setName("mike"+i);
            user.setPassword("123"+i);
            user.setCreatedAt(new Date());
            int insert = sqlSession.insert(STATEMENT+".addUser", user);
            System.out.println(user.getId());
        }
        sqlSession.commit();

    }

    @Test
    @DisplayName("修改")
    public void update(){
        User user=new User();
        user.setName("lucy");
        user.setPassword("321");
        user.setCreatedAt(new Date());
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

    @Test
    @DisplayName("分页")
    public void doPage(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        UserPageInput pageInput=UserPageInput.builder().pageNo(1).pageSize(2).name("mike").build();
        Integer pageSize = pageInput.getPageSize();
        Integer pageNo = pageInput.getPageNo();
        pageInput.setLimit((pageNo-1)*pageSize);
        List<User> records = mapper.getPageRecordsByPageInput(pageInput);
        Integer recordCount = mapper.getTotalByPageInput(pageInput);
        UserPageOutput<User> userPageOutput=new UserPageOutput<>();

        Integer total = recordCount / pageSize;
        if((recordCount % pageSize)>0){
            total++;
        }
        userPageOutput.setRecordCount(recordCount);
        userPageOutput.setRecords(records);
        userPageOutput.setTotal(total);
        System.out.println(userPageOutput);
    }

    @Test
    @DisplayName("条件集合查询")
    public void queryByList(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<Integer> list=new ArrayList<>();
        list.add(2);
        list.add(3);
        list.add(4);
        List<User> users = mapper.getUsersByIds(list);
        users.stream().forEach(user -> System.out.println(user));
    }


    @Test
    @DisplayName("注解开发：新增")
    public void annotationAdd(){
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        for(int i=0;i<20;i++){
            Order order=new Order();
            order.setStatus(1);
            order.setName("订单00"+i);
            order.setCreatedAt(new Date());
            mapper.addOrder(order);
        }
        sqlSession.commit();
    }

    @Test
    @DisplayName("注解开发：修改")
    public void updateOrder(){
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        Order order=new Order();
        order.setId(21);
        order.setStatus(2);
        order.setName("订单");
        mapper.updateOrder(order);
        sqlSession.commit();
    }

    @Test
    @DisplayName("注解开发：删除")
    public void deleteOrder(){
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        mapper.deleteOrder(21);
        sqlSession.commit();
    }

    @Test
    @DisplayName("注解开发：查询")
    public void findAllByAnnotation(){
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        List<Order> all = mapper.findAll();
        System.out.println(all);
        System.out.println("------------------------");
        Order order006 = mapper.findOne(27, "订单006");
        System.out.println(order006);
        List<Order> dynamicSql = mapper.dynamicSql("订单006", null);
        System.out.println(dynamicSql);
    }

    @Test
    @DisplayName("xml配置文件：一对多结果封装")
    public void oneToManyByXml(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.queryRolesByUid(3);
        System.out.println(user);
    }

    @Test
    @DisplayName("xml配置：一对一结果封装")
    public void oneToOneByXml(){
        GoodsMapper mapper = sqlSession.getMapper(GoodsMapper.class);
        Goods goods = mapper.queryOneToOne(1);
        System.out.println(goods);
    }

    @Test
    @DisplayName("注解方式：一对一查询")
    public void oneToOneByAnnotation(){
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        Order order = mapper.oneToOne(22);
        System.out.println(order);
    }

    @Test
    @DisplayName("注解方式:一对查询")
    public void oneToManyByAnnotation(){
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        Order order = mapper.oneToMany(22);
        System.out.println(order);
    }

}
