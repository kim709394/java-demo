package com.kim.springframework;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author huangjie
 * @description   注解方式实现声明式事务
 * @date 2022/1/18
 */
@Configuration
@PropertySource("classpath:jdbc.properties")
@ComponentScan("com.kim.springframework.transaction")
//开启事务注解的支持
@EnableTransactionManagement
public class TransactionApplication {

    @Value("${driver}")
    private String driver;
    @Value("${url}")
    private String url;
    @Value("${user}")
    private String user;
    @Value("${password}")
    private String password;

    //配置druid数据源
    @Bean
    public DataSource druidDataSource(){
        DruidDataSource druidDataSource=new DruidDataSource();
        druidDataSource.setDriverClassName(driver);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(user);
        druidDataSource.setPassword(password);
        return druidDataSource;
    }

    //事务管理器
    @Bean
    public PlatformTransactionManager transactionManager(@Autowired DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);

    }

    //jdbc template
    @Bean
    public JdbcTemplate jdbcTemplate(@Autowired  DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }



}
