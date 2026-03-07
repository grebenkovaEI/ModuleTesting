package ru.stepup.db.students;

import ru.stepup.db._base.BaseMainDao;
import ru.stepup.db.students.entity.StudentEntity;

import java.util.List;

public class StudentDao extends BaseMainDao {

    public List<StudentEntity> findAllStudents() {
        String query = """
                SELECT *
                FROM STUDENTS;
                """;

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .mapToBean(StudentEntity.class)
                .list()
        );
    }

    public StudentEntity findStudentByName(String name) {
        String query = """
                SELECT *
                FROM STUDENTS
                WHERE NAME = :name
                """;

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .bind("name", name)
                .mapToBean(StudentEntity.class)
                .one()
        );
    }
}
