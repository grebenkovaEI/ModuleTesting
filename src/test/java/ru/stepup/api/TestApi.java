package ru.stepup.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.stepup.api.students.StudentApi;
import ru.stepup.api.students.entity.response.StudentDto;
import ru.stepup.db.students.StudentDao;
import ru.stepup.db.students.entity.StudentEntity;

@Slf4j
class TestApi {
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








}
