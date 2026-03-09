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
    StudentDao dao = new StudentDao();
    StudentApi api = new StudentApi();


    //1. get /student/{id} возвращает JSON студента с указанным ID и заполненным именем, если такой есть в базе, код 200.
    @Test
    void testGetStudentById_200() {
        StudentDto st = api.getStudentById_200(161);
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
        int id = 1;
        String name = "Petr";
        String email = "q@mail.ru";
        api.getStudentById_404(1);
        String str = String.format("{\"id\": %d, \"name\": \"%s\", \"email\": \"%s\"}", id, name, email);
        StudentDto st = api.postStudent_201(str);
        StudentEntity entity = dao.findStudentByName("Petr");
        api.getStudentById_200(entity.getId());
        System.out.println("testPostNewStudent: " + st);
    }
//4. post /student обновляет студента в базе, если студент с таким ID ранее был, при этом имя заполнено, код 201.
    @Test
    void testUpdateStudent() {
        int id = 193;
        String newName = "Sergei";
        String email = "ww@mail.ru";
        String str = String.format("{\"id\": %d, \"name\": \"%s\", \"email\": \"%s\"}", id, newName, email);
        StudentDto st = api.postStudent_201(str);
        StudentEntity entity = dao.findStudentById(id);
        entity.getName().equals(newName);
    }
//5. post /student добавляет студента в базу, если ID null, то возвращается назначенный ID, код 201.
    @Test
    void testAddStudentIdNull() {
        String name = "Oleg";
        String email = "ee@mail.ru";
        String str = String.format("{\"name\": \"%s\", \"email\": \"%s\"}", name, email);
        StudentDto st = api.postStudent_201(str);
        StudentEntity entity = dao.findStudentByName(name);
        System.out.println(api.getStudentById_200(entity.getId()));
    }
//6. post /student возвращает код 400, если имя не заполнено.
    @Test
    void testAddStudentNameNull() {
        String name = "";
        String email = "t@mail.ru";
        String str = String.format("{\"name\": \"%s\", \"email\": \"%s\"}", name, email);
        api.postStudent_400(str);
    }
//7. delete /student/{id} удаляет студента с указанным ID из базы, код 200.
    @Test
    void testDeleteStudentById() {
        api.deleteStudentById(33);
    }

//8. delete /student/{id} возвращает код 404, если студента с таким ID в базе нет.
    @Test
    void testDeleteStudentById_404() {
        api.deleteStudentById_404(10);
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
        StudentEntity entityFromDb = dao.findTopStudent();
        StudentDto st = api.getTopStudent_One();

        Assertions.assertEquals(st.getId(), entityFromDb.getId());
        Assertions.assertEquals(st.getName(), entityFromDb.getName());
    }

//12. get /topStudent код 200 и несколько студентов, если у них всех эта оценка максимальная и при этом они равны по количеству оценок.
    @Test
    void testGetTopStudent_Few() {
        String student1 = "{\"id\": 1, \"name\": \"Olga\", \"grades\": [5, 4]}";
        String student2 = "{\"id\": 2, \"name\": \"Irina\", \"grades\": [4, 5]}";
        api.postStudent_201(student1);
        api.postStudent_201(student2);

        List<StudentDto> list =  api.getTopStudent_Few();
        Assertions.assertEquals(2, list.size());
        Assertions.assertSame(student1, list.getFirst().toString());
        Assertions.assertSame(student2, list.getLast().toString());
    }
}
