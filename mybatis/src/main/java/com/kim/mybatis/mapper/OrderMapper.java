package com.kim.mybatis.mapper;

import com.kim.mybatis.pojo.Goods;
import com.kim.mybatis.pojo.Order;
import com.kim.mybatis.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author huangjie
 * @description
 * @date 2021/11/2
 */
public interface OrderMapper {

    @Insert("insert into t_order (name,status,created_at) values (#{name},#{status},#{createdAt})")
    void addOrder(Order order);

    @Update("update t_order set name = #{name},status = #{status} where id= #{id}")
    void updateOrder(Order order);

    @Select("select * from t_order")
    List<Order> findAll();

    @SelectProvider(type = FindOne.class,method = "findOne")
    Order findOne(@Param("id") Integer id,@Param("name") String name);

    class FindOne{
        public static String findOne(@Param("id") Integer id,@Param("name") String name){
            return "select * from t_order where id = #{id} and name = #{name}";
        }
    }

    @Delete("delete from t_order where id = #{id}")
    void deleteOrder(Integer id);

    //动态sql
    @Select({
            "<script>",
                "select * from t_order ",
                "<trim prefix='where' prefixOverrides='and|or'>",
                    "<if test='name !=null'> and name = #{name} </if>",
                    "<if test='status !=null'> and status = #{status} </if>",
                "</trim>",
            "</script>"})
    List<Order> dynamicSql(@Param("name") String name,@Param("status") Integer status);


    //一对一查询
    @Select("select * from t_order where id=#{id}")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "name",property = "name"),
            @Result(column = "status",property = "status"),
            @Result(column = "created_at",property = "createdAt"),
            @Result(column = "deleted_at",property = "deletedAt"),
            @Result(column = "uid",property = "user",javaType = User.class,one = @One(
                    select = "com.kim.mybatis.mapper.UserMapper.findById"))
    })
    Order oneToOne(Integer id);

    //一对多查询
    @Select("select * from t_order where id=#{id}")
    @Results({
            @Result(id=true,column = "id", property="id"),
            @Result(column = "name", property="name"),
            @Result(column = "status", property="status"),
            @Result(column = "created_at", property="createdAt"),
            @Result(column = "deleted_at", property="deletedAt"),
            @Result(column = "id", property="goodsList",javaType = List.class,many = @Many(
                    select = "com.kim.mybatis.mapper.GoodsMapper.queryGoodsByOid")),
    })
    Order oneToMany(Integer id);

}
