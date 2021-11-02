package com.kim.mybatis.mapper;

import com.kim.mybatis.pojo.Order;
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

}
