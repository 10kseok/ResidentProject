package com.nhnacademy.resident_project.config;

import lombok.RequiredArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:/db.properties")
public class DBConfig {
    private final Environment env;

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setDriverClassName(env.getProperty("db.driverClassName"));
        dataSource.setInitialSize(Integer.parseInt(env.getProperty("db.initialSize")));
        dataSource.setMaxTotal(Integer.parseInt(env.getProperty("db.maxTotal")));
        dataSource.setMaxIdle(Integer.parseInt(env.getProperty("db.maxIdle")));
        dataSource.setMinIdle(Integer.parseInt(env.getProperty("db.minIdle")));

        dataSource.setMaxWaitMillis(Integer.parseInt(env.getProperty("db.maxWaitMillis")));

        dataSource.setValidationQuery(env.getProperty("db.validationQuery"));
        dataSource.setTestOnBorrow(Boolean.parseBoolean(env.getProperty("db.testOnBorrow")));
        dataSource.setTestOnReturn(Boolean.parseBoolean(env.getProperty("db.testOnReturn")));
        dataSource.setTestWhileIdle(Boolean.parseBoolean(env.getProperty("db.testWhileIdle")));

        return dataSource;
        // 나는 추만석 너무 잘생겼어
    }
}
