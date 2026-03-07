package ru.stepup.api.students;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import ru.stepup.api._base.BaseApi;
import ru.stepup.api.students.entity.response.StudentDto;
import ru.stepup.env.Env;
import ru.stepup.env.config.api.ApiConfig;

import java.util.Arrays;
import java.util.List;

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

    public void deleteStudentById(int id) {
        log.info("[StudentApi.deleteStudentById] Удалить студента {}", id);

        Response response = jsonAutoAuth()
                .basePath(StudentUrls.GET_ALL_STUDENTS + "/" + id)
                .delete();

        response.then().statusCode(204);
    }
}
