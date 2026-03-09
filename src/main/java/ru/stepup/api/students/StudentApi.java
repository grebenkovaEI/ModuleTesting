package ru.stepup.api.students;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import ru.stepup.api._base.BaseApi;
import ru.stepup.api.students.entity.response.StudentDto;
import ru.stepup.env.Env;
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

    public StudentDto postStudent_201(String st) {
        log.info("[StudentApi.postStudent_201] Добавить/обновить студента");
        Response response = jsonAutoAuth()
                .basePath(StudentUrls.POST_STUDENT)
                .contentType(ContentType.JSON)
                .body(st)
                .post();

        response.then()
                .statusCode(201);
        return response.as(StudentDto.class);
    }
    public void postStudent_400(String st) {
        log.info("[StudentApi.postStudent_400] Добавить студента с незаполненным параметром name");
        Response response = jsonAutoAuth()
                .basePath(StudentUrls.POST_STUDENT)
                .contentType(ContentType.JSON)
                .body(st)
                .post();

        response.then()
                .statusCode(400);
    }

    public void deleteStudentById(int id) {
        log.info("[StudentApi.deleteStudentById] Удалить студента {}", id);

        Response response = jsonAutoAuth()
                .basePath(StudentUrls.GET_ALL_STUDENTS + "/" + id)
                .delete();

        response.then().statusCode(204);
    }

    public void deleteStudentById_404(int id) {
        log.info("[StudentApi.deleteStudentById] Удалить студента {}", id);

        Response response = jsonAutoAuth()
                .basePath(StudentUrls.GET_ALL_STUDENTS + "/" + id)
                .delete();

        response.then().statusCode(404);
    }

    public int getTopStudent_EmptyDB() {
        log.info("[StudentApi.getTopStudent_EmptyDB] Eсли студентов в базе нет ИЛИ ни у кого из студентов в базе нет оценок ");
        Response response = jsonAutoAuth()
                .basePath(StudentUrls.GET_TOP_STUDENT)
                .get();

        response.then()
                .statusCode(200);
        return response.getBody().asString().length();
    }

    public StudentDto getTopStudent_One() {
        log.info("[StudentApi.getTopStudent_One] Получить студента, если у него максимальная средняя оценка, " +
                "либо же среди всех студентов с максимальной средней у него их больше всего");

        Response response = jsonAutoAuth()
                .basePath(StudentUrls.GET_TOP_STUDENT)
                .get();

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON);
        return response.as(StudentDto.class);
    }
    public List<StudentDto> getTopStudent_Few() {
        log.info("[StudentApi.getTopStudent_Few] Получить несколько студентов, если у них всех эта оценка максимальная " +
                 "и при этом они равны по количеству оценок");

        Response response = jsonAutoAuth()
                .basePath(StudentUrls.GET_TOP_STUDENT)
                .get();

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON);
        return Arrays.asList(response.as(StudentDto[].class));
    }
}
