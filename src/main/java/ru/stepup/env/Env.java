package ru.stepup.env;

import lombok.experimental.UtilityClass;
import org.aeonbits.owner.ConfigFactory;
import ru.stepup.env.config.api.ApiConfig;
import ru.stepup.env.config.db.DbConfig;

@UtilityClass
public class Env {

    public static final class DB {
        public  static final DbConfig DB_CONFIG = ConfigFactory.create(DbConfig.class);
    }
    public static final class API {
        public  static final ApiConfig API_CONFIG = ConfigFactory.create(ApiConfig.class);
    }
}
