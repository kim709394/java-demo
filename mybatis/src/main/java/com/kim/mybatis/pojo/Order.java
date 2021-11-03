package com.kim.mybatis.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author huangjie
 * @description     订单
 * @date 2021/11/2
 */
@Data
public class Order extends BasePojo {

    private Integer status;     //订单状态

    private User user;  //从属用户

    private List<Goods> goodsList;


    @Override
    public String toString() {
        return "Order{" + "status=" + status + ", user=" + user + ", id=" + id + ", name='" + name + '\'' + ", createdAt=" + createdAt + ", deletedAt=" + deletedAt +
                ",goodsList="+goodsList+'}';
    }
}
