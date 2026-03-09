package ru.stepup.api;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stepup.api.students.StudentApi;
import ru.stepup.api.students.StudentUrls;
import ru.stepup.api.students.entity.response.StudentDto;
import ru.stepup.db.students.StudentDao;
import ru.stepup.db.students.entity.StudentEntity;

import java.util.List;

import static io.restassured.RestAssured.given;

@Slf4j
class TestApi {
    //StudentDao dao = new StudentDao();
    StudentApi api = new StudentApi();


    //1. get /student/{id} возвращает JSON студента с указанным ID и заполненным именем, если такой есть в базе, код 200.
    @Test
    void testGetStudentById_200() {
        int id = 1;
        String name = "Petr";
        String str = String.format("{\"id\": %d, \"name\": \"%s\"}", id, name);
        api.postStudent_201(str);

        StudentDto st = api.getStudentById_200(1);
        System.out.println("testGetStudentById_200: " + st);
    }
    //2. get /student/{id} возвращает код 404, если студента с данным ID в базе нет.
    @Test
    void testGetStudentById_404() {
        api.getStudentById_404(0);
    }

    //3. post /student добавляет студента в базу, если студента с таким ID ранее не было, при этом имя заполнено, код 201.
    @Test
    void testPostNewStudent() {
        int id = 4;
        String name = "Petr";
        api.getStudentById_404(4);
        String str = String.format("{\"id\": %d, \"name\": \"%s\"}", id, name);
        api.postStudent_201(str);
        Assertions.assertNotNull(api.getStudentById_200(id).getName());
    }
//4. post /student обновляет студента в базе, если студент с таким ID ранее был, при этом имя заполнено, код 201.
    @Test
    void testUpdateStudent() {
        int id = 1;
        String newName = "Sergeei";
        String str = String.format("{\"id\": %d, \"name\": \"%s\"}", id, newName);
        api.postStudent_201(str);
        Assertions.assertEquals(newName, api.getStudentById_200(id).getName());
    }
//5. post /student добавляет студента в базу, если ID null, то возвращается назначенный ID, код 201.
    @Test
    void testAddStudentIdNull() {
        String name = "Oleg";
        String str = String.format("{\"name\": \"%s\"}", name);
        api.postStudent_201(str);
    }
//6. post /student возвращает код 400, если имя не заполнено.
    @Test
    void testAddStudentNameNull() {
        int id = 1;
        String str = String.format("{\"id\": %d}", id);
        api.postStudent_400(str);
    }
//7. delete /student/{id} удаляет студента с указанным ID из базы, код 200.
    @Test
    void testDeleteStudentById() {
        api.deleteStudentById(7);
    }

//8. delete /student/{id} возвращает код 404, если студента с таким ID в базе нет.
    @Test
    void testDeleteStudentById_404() {
        api.deleteStudentById_404(9);
    }

//9. get /topStudent код 200 и пустое тело, если студентов в базе нет
    @Test
    void testTopStudent_EmptyStudents() {
        int length = api.getTopStudent_EmptyDB();
        Assertions.assertEquals(0, length);
    }

//10. get /topStudent код 200 и пустое тело, если ни у кого из студентов в базе нет оценок ()
    @Test
    void testTopStudent_EmptyGrades() {
        int length = api.getTopStudent_EmptyDB();
        Assertions.assertEquals(0, length);
    }

//11. get /topStudent код 200 и один студент, если у него максимальная средняя оценка, либо же среди всех студентов с максимальной средней у него их больше всего.
    @Test
    void testGetTopStudent_One() {
        String student1 = "{\"id\": 10, \"name\": \"Olga\", \"marks\": [5, 5]}";
        String student2 = "{\"id\": 11, \"name\": \"Irina\", \"marks\": [3, 3, 3]}";
        api.postStudent_201(student1);
        api.postStudent_201(student2);

        StudentDto st = api.getTopStudent_One();
        Assertions.assertEquals(10, st.getId());
    }

//12. get /topStudent код 200 и несколько студентов, если у них всех эта оценка максимальная и при этом они равны по количеству оценок.
    @Test
    void testGetTopStudent_Few() {
        String student1 = "{\"id\": 12, \"name\": \"Olga\", \"marks\": [5, 5, 5]}";
        String student2 = "{\"id\": 13, \"name\": \"Irina\", \"marks\": [5, 5, 5]}";
        api.postStudent_201(student1);
        api.postStudent_201(student2);

        List<StudentDto> list =  api.getTopStudent_Few();
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(12, list.getFirst().getId());
        Assertions.assertEquals(13, list.getLast().getId());
    }
}
