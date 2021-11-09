package com.kim.mybatis.plugins;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author huangjie
 * @description   Executor拦截器插件
 * @date 2021/11/9
 */
@Intercepts(@Signature(
        type = Executor.class,
        method = "query",
        args={MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
))
public class ExecutorPlugin implements Interceptor {


    private Map<String,String> properties=new HashMap<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("Executor:query前置拦截逻辑");
        System.out.println("获取的properties："+properties);
        Object proceed = null;
        try {
            proceed = invocation.proceed();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Executor:query异常拦截逻辑");
        } finally {
            System.out.println("Executor:query后置拦截逻辑");
        }

        return proceed;
    }

    @Override
    public Object plugin(Object target) {
        System.out.println("plugin方法");
        return plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        properties.putAll(this.properties);
    }
}
