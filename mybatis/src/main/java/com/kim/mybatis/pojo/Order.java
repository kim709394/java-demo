package com.kim.mybatis.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author huangjie
 * @description     订单
 * @date 2021/11/2
 */
@Data
public class Order extends BasePojo implements Serializable {

    private static final long serialVersionUID = -4009751163281338673L;
    private Integer status;     //订单状态

    private User user;  //从属用户

    private List<Goods> goodsList;


    @Override
    public String toString() {
        System.out.println("order call toString");
        return "Order{" + "status=" + status + ", user=" + user + ", id=" + id + ", name='" + name + '\'' + ", createdAt=" + createdAt + ", deletedAt=" + deletedAt +
                ",goodsList="+goodsList+'}';
    }
}
