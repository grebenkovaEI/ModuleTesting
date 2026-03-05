package ru.stepup.api._base;

import io.restassured.RestAssured;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import ru.stepup.env.config.api.ApiConfig;
import io.restassured.specification.RequestSpecification;

@RequiredArgsConstructor
public class BaseApi {

    protected final ApiConfig CONFIG;

    protected RequestSpecification buildRequest(ContentType contentType) {
        return RestAssured.given()
                .config()
    }
}
