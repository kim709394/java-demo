package com.kim.mybatis.pojo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author huangjie
 * @description  角色
 * @date 2021/11/3
 */
@Data
public class Role extends BasePojo implements Serializable {

    private static final long serialVersionUID = -506299170570734284L;
    private String code;

    @Override
    public String toString() {
        return "Role{" + "code='" + code + '\'' + ", id=" + id + ", name='" + name + '\'' + ", createdAt=" + createdAt + ", deletedAt=" + deletedAt + '}';
    }
}
