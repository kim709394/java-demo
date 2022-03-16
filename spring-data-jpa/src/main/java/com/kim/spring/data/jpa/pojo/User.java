package com.kim.spring.data.jpa.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author huangjie
 * @description 用户实体
 * @date 2021-10-31
 */

@Data
@Entity //标注这是一个数据库表和实体对应
@Table(name = "t_user") //标注应对数据哪张表
public class User implements Serializable {


    private static final long serialVersionUID = -9029862553254325939L;

    @Id //标注这是一个主键字段
    //指定主键生成策略
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //标注属性字段名和数据库字段名之间的对应关系
    @Column(name = "name")
    private String name;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "deleted_at")
    private Date deletedAt;

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", createdAt=" + createdAt + ", deletedAt=" + deletedAt + '}';
    }
}
