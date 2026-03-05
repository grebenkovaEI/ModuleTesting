package ru.stepup.env.config.api;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:env",
        "system:properties",
        "classpath:config/${env}/apiConfig.properties"
})
public interface ApiConfig extends Config {

    @Config.Key("api.url")
    String url();
}
