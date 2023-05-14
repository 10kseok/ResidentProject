package com.nhnacademy.resident_project.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
public class TestDBConfig {
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setUrl("jdbc:h2:mem:testdb;MODE=MYSQL;" +
                "CASE_INSENSITIVE_IDENTIFIERS=TRUE;" +
                "INIT=RUNSCRIPT FROM 'classpath:/script/resident-h2.sql'");

        dataSource.setDriverClassName("org.h2.Driver");

        dataSource.setInitialSize(10);
        dataSource.setMaxTotal(10);
        dataSource.setMaxIdle(10);
        dataSource.setMinIdle(10);

        dataSource.setMaxWaitMillis(2000);

        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(true);
        dataSource.setTestWhileIdle(true);

        return dataSource;
    }
}
