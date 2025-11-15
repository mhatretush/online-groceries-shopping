package com.ogs.shopping.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@AllArgsConstructor
public class DataSourceConfig {
    private DataSource dataSource;

    @PostConstruct
    public void printPoolInfo() throws SQLException {
        HikariDataSource hikariDataSource = (HikariDataSource) dataSource;

        System.out.println("Connected Pool Info");
        System.out.println("Max Pool Name: " + hikariDataSource.getPoolName());
        System.out.println("Max Pool Size: " + hikariDataSource.getMaximumPoolSize());
        System.out.println("Min idle: " + hikariDataSource.getMinimumIdle());
        System.out.println("Max Pool Size: " + hikariDataSource.getJdbcUrl());
    }
}