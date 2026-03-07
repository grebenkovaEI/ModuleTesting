package ru.stepup.db;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.stepup.api.students.StudentApi;
import ru.stepup.api.students.entity.response.StudentDto;
import ru.stepup.db.students.StudentDao;
import ru.stepup.db.students.entity.StudentEntity;

import java.util.List;

@Slf4j
class TestDataBase {

    StudentDao dao = new StudentDao();
    StudentApi api = new StudentApi();

    @Test
    void testDataSource() {

        try {
            StudentEntity entity = dao.findStudentByName("Student_21905bd0");
            api.deleteStudentById(entity.getId());
        } catch (Exception e) {
            log.error("Удаление не прошло, такого студента нет");
        }

        System.out.println("Что-то");






//       List<StudentEntity> students = dao.findAllStudents();
//       List<StudentDto> studentDaos = api.getAllStudents();
//
//       System.out.println("DAO " + students);
//       System.out.println("API " + studentDaos);
    }
}
