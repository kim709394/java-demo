package com.kim.mybatis.pojo;

import lombok.Data;

/**
 * @author huangjie
 * @description     商品
 * @date 2021/11/3
 */
@Data
public class Goods extends BasePojo{

    private Integer price;

    private Order order;

    @Override
    public String toString() {
        return "Goods{" + "price=" + price + ", id=" + id + ", name='" + name + '\'' + ", createdAt=" + createdAt + ", deletedAt=" + deletedAt +
                ", order="+order+'}';
    }
}
