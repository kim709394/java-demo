package com.kim.mybatis.plugins;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

/**
 * @author huangjie
 * @description  StatementHandler  sql语句执行预处理拦截器
 * @date 2021-11-09
 */
@Intercepts(
        @Signature( type = StatementHandler.class,
                    method = "prepare",
                    args = {Connection.class, Integer.class}
        )
)
public class StatementHandlerPlugin implements Interceptor {


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
        System.out.println("StatementHandler前置增强");
        return invocation.proceed();
    }
}
