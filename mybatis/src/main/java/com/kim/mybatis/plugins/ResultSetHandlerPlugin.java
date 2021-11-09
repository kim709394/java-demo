package com.kim.mybatis.plugins;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.Properties;

/**
 * @author huangjie
 * @description  ResultSetHandler结果集封装拦截器
 * @date 2021-11-09
 */
@Intercepts(@Signature(type = ResultSetHandler.class,method = "handleResultSets",
    args={Statement.class}
))
public class ResultSetHandlerPlugin implements Interceptor {

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
        System.out.println("ResultSetHandler前置增强");
        return invocation.proceed();
    }
}
