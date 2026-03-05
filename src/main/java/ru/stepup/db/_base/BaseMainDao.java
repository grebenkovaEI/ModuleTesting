package ru.stepup.db._base;

import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.ColumnMappers;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import ru.stepup.db.DataSourceProvider;
import ru.stepup.env.Env;

@Slf4j
public class BaseMainDao {

    protected Jdbi jdbi;

    protected  BaseMainDao() {
        log.info("Инициализация базового DAO");

        jdbi = Jdbi.create(DataSourceProvider.getH2DataSource(Env.DB.DB_CONFIG));
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.getConfig(ColumnMappers.class).setCoalesceNullPrimitivesToDefaults(false);


        log.info("DAO успешно загружен");
    }
}
