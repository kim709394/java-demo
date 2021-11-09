package com.kim.mybatis.plugins;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.*;

import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * @author huangjie
 * @description ParameterHandler参数设置拦截器
 * @date 2021-11-09
 */
@Intercepts(@Signature(
        type = ParameterHandler.class,method = "setParameters",args={PreparedStatement.class}
))
public class ParameterHandlerPlugin  implements Interceptor {

    @Override
    public Object plugin(Object target) {
        if(target == null){
            return null;
        }
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("ParameterHandler前置增强");
        return invocation.proceed();
    }
}
