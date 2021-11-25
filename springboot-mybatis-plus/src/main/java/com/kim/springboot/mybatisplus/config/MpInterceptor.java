package com.kim.springboot.mybatisplus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.IllegalSQLInnerInterceptor;
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
        /**
         * 使用多个功能需要注意顺序关系,建议使用如下顺序
         *
         * 多租户,动态表名
         * 分页,乐观锁
         * sql性能规范,防止全表更新与删除
         * 总结: 对sql进行单次改造的优先放入,不对sql进行改造的最后放入
         * */
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //多租户插件，注意要配置在分页插件之前，否则分页数据记录统计不会加入租户字段过滤
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new MyTenantLine()));
        //分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //sql性能规范插件
        interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        //阻止全表更新和删除插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        return interceptor;
    }


    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
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
