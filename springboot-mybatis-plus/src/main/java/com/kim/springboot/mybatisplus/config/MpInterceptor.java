package com.kim.springboot.mybatisplus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author huangjie
 * @description     mybatis-plus插件配置
 * @date 2021/11/24
 */
@SpringBootConfiguration
public class MpInterceptor {

    //插件配置
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //阻止全表更新和删除插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        //多租户插件
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new MyTenantLine()));
        return interceptor;
    }

    //行级租户处理器
    public static class MyTenantLine implements TenantLineHandler{

        private static ThreadLocal<Long> tenantId=new ThreadLocal<>();

        public static void setTenantId(Long id){
            tenantId.set(id);
        }

        /**
         * 获取租户唯一标识，每次查询都从这里获取，然后进行过滤，
         * 可从每次请求前端传入，这里模拟使用ThreadLocal获取
         */
        @Override
        public Expression getTenantId() {
            return new LongValue(tenantId.get());
        }
        //获取租户唯一标识字段名，默认是tenant_id
        @Override
        public String getTenantIdColumn() {
            return TenantLineHandler.super.getTenantIdColumn();
        }

        //控制哪些表需要过滤租户字段，哪些不需要过滤租户字段
        @Override
        public boolean ignoreTable(String tableName) {
            return TenantLineHandler.super.ignoreTable(tableName);
        }
    }



}
