package ru.stepup.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.stepup.env.config.db.DbConfig;

import javax.sql.DataSource;

@UtilityClass
@Slf4j
public class DataSourceProvider {
    public static DataSource getH2DataSource(DbConfig config){
        log.info("[DataSourceProvider.getH2DataSource] Инициализация H2");

        HikariConfig hikariConfig = new HikariConfig();

//        hikariConfig.setJdbcUrl(config.h2JdbcUrl());
        hikariConfig.setJdbcUrl("jdbc:h2:tcp://localhost:9092/./studentsdb");
//        hikariConfig.setUsername(config.user());
        hikariConfig.setUsername("sa");
//        hikariConfig.setPassword(config.password());
        hikariConfig.setPassword("sa");

        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setConnectionTimeout(30000);

        hikariConfig.setPoolName("H2");
        hikariConfig.setAutoCommit(true);

        log.info("[DataSourceProvider.getH2DataSource] Конфиг настроен");
        return new HikariDataSource(hikariConfig);
    }
}
