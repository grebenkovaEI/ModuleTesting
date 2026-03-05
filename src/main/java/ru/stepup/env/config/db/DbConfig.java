package ru.stepup.env.config.db;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:env",
        "system:properties",
        "classpath:config/${env}/dbConfig.properties"
})
public interface DbConfig extends Config {
    @Key("db.h2.jdbcUrl")
    String h2JdbcUrl();
    @Key("db.h2.user")
    String  user();
    @Key("db.h2.password")
    String password();
}
