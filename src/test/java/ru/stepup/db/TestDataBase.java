package ru.stepup.db;

import org.junit.jupiter.api.Test;
import ru.stepup.db.students.StudentDao;
import ru.stepup.db.students.entity.StudentEntity;

import java.util.List;

class TestDataBase {

    StudentDao dao = new StudentDao();

    @Test
    void testDataSource() {
       List<StudentEntity> students = dao.findAllStudents();

        System.out.println(students);
    }
}
