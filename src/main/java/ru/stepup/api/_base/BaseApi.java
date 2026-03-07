package ru.stepup.api._base;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import ru.stepup.env.config.api.ApiConfig;
import io.restassured.specification.RequestSpecification;

import java.util.List;

@RequiredArgsConstructor
public class BaseApi {

    protected final ApiConfig CONFIG;

    protected RequestSpecification jsonAutoAuth() {
        return  buildRequest(ContentType.JSON);
    }

    protected RequestSpecification buildRequest(ContentType contentType) {
        return RestAssured.given()
                .config(creatrConfig())
                .relaxedHTTPSValidation()
                .contentType(contentType)
                .baseUri(CONFIG.url())
                .filters(getFilters());
    }

    private RestAssuredConfig creatrConfig() {
        return RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 5000)
                );
    }

    private List<Filter> getFilters() {
        return List.of(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter()
        );
    }
}
