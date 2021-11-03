package com.kim.mybatis.pojo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author huangjie
 * @description  用户实体
 * @date 2021-10-31
 */
@Data
public class User extends BasePojo implements Serializable {


    private static final long serialVersionUID = -9029862553254325939L;
    private String password;

    private List<Role> roles;

    @Override
    public String toString() {
        return "User{" + "password='" + password + '\'' + ", roles=" + roles + ", id=" + id + ", name='" + name + '\'' + ", createdAt=" + createdAt + ", deletedAt=" + deletedAt + '}';
    }
}
