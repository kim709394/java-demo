package com.kim.mybatis.plugins;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author huangjie
 * @description Executor数据访问执行器拦截器插件
 * @date 2021/11/9
 */
@Intercepts(@Signature(type = Executor.class,  //要拦截的方法所在的类
        method = "query",       //要拦截的方法的名称
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}    //要拦截的方法的全部参数类型
))
public class ExecutorPlugin implements Interceptor {


    private Map<String, Object> properties = new HashMap<>();

    //拦截逻辑
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("Executor:query前置拦截逻辑");
        System.out.println("获取的properties：" + properties);
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


    //被拦截的封装的类
    @Override
    public Object plugin(Object target) {
        System.out.println("plugin方法");
        return Plugin.wrap(target, this);
    }

    //从配置文件获取配置的参数
    @Override
    public void setProperties(Properties properties) {
        properties.forEach((o, o2) -> {
            this.properties.put((String) o, o2);
        });
    }
}
