package ru.stepup.api.students;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.groovy.ast.expr.UnaryMinusExpression;
import org.hamcrest.Matchers;
import ru.stepup.api._base.BaseApi;
import ru.stepup.api.students.entity.response.StudentDto;
import ru.stepup.env.Env;
import ru.stepup.env.config.api.ApiConfig;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@Slf4j
public class StudentApi extends BaseApi {
    public StudentApi() {
        super(Env.API.API_CONFIG);
    }

    public List<StudentDto> getAllStudents() {
        log.info("[StudentApi.getAllStudents] Получить всех студентов");

        Response response = jsonAutoAuth()
                .basePath(StudentUrls.GET_ALL_STUDENTS)
                .get();

        response.then().statusCode(200);
        return Arrays.asList(response.as(StudentDto[].class));
    }

    public StudentDto getStudentById_200(int id) {
        log.info("[StudentApi.getStudentById] Получить студента {}", id);

        Response response = jsonAutoAuth()
                .basePath(StudentUrls.GET_ALL_STUDENTS + "/" + id)
                .get();

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", Matchers.equalTo(id))
                .body("name", notNullValue());
        return response.as(StudentDto.class);
    }

    public void getStudentById_404(int id) {
        log.info("[StudentApi.getStudentById] Получить студента {}", id);

        Response response = jsonAutoAuth()
                .basePath(StudentUrls.GET_ALL_STUDENTS + "/" + id)
                .get();

        response.then().statusCode(404);
    }




    public void deleteStudentById(int id) {
        log.info("[StudentApi.deleteStudentById] Удалить студента {}", id);

        Response response = jsonAutoAuth()
                .basePath(StudentUrls.GET_ALL_STUDENTS + "/" + id)
                .delete();

        response.then().statusCode(204);
    }
}
