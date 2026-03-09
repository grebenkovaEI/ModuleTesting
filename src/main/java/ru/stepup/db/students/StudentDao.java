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

    public StudentEntity findStudentById(int id) {
        String query = """
                SELECT *
                FROM STUDENTS
                WHERE ID = :id
                """;

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .bind("id", id)
                .mapToBean(StudentEntity.class)
                .one()
        );
    }

    public StudentEntity findTopStudent() {
        String query = """
                SELECT s.ID, s.NAME, AVG(sg.GRADE) AS average_grade, COUNT(sg.GRADE) AS grade_count
                FROM STUDENTS s
                LEFT JOIN STUDENT_GRADES sg ON s.ID = sg.STUDENT_ID
                GROUP BY s.ID
                ORDER BY average_grade DESC, grade_count DESC
                LIMIT 1
                """;

        return jdbi.withHandle(handle -> handle.createQuery(query)
                .mapToBean(StudentEntity.class)
                .one()
        );
    }



}
