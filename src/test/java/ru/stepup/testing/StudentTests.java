package ru.stepup.testing;

import org.junit.jupiter.api.*;

import java.util.List;

public class StudentTests {
    private Student student;

    @BeforeEach
    void createStudent() {
        student = new Student("Oleg");
    }

    @Test
    @DisplayName("Список можно изменять, но меняется его копия")
    void gradesEncapsulation() {
        student.addGrade(4);
        student.addGrade(3);
        List<Integer> grades = student.getGrades();
        Assertions.assertDoesNotThrow(() -> grades.add(5));
        Assertions.assertDoesNotThrow(() -> grades.remove(Integer.valueOf(4)));
        Assertions.assertDoesNotThrow(grades::clear);

        List<Integer> list = student.getGrades();
        Assertions.assertEquals(2, list.size());
        Assertions.assertTrue(list.contains(4));
        Assertions.assertTrue(list.contains(3));
    }

    @Test
    @DisplayName("Успешная проверка методов Equals и HashCode")
    void testEqualsAndHashCode() {
        student.addGrade(3);
        Student student2 = new Student("Oleg");
        student2.addGrade(3);

        Assertions.assertEquals(student, student2);
        Assertions.assertEquals(student.hashCode(), student2.hashCode());
    }

    @Test
    @DisplayName("Неуспешная проверка метода Equals")
    void testNotEquals() {
        //Student student = new Student("Oleg");
        student.addGrade(4);

        Student student2 = new Student("Petr");
        student2.addGrade(4);

        Student student3 = new Student("Oleg");
        student3.addGrade(5);


        Assertions.assertNotEquals(student, student2);
        Assertions.assertNotEquals(student, student3);
    }

    @Test
    @DisplayName("Успешная проверка метода ToString")
    void testToString() {
        student.addGrade(3);

        String str = student.toString();
        Assertions.assertTrue(str.contains("Oleg"));
        Assertions.assertTrue(str.contains("3"));
    }

    @Test
    @DisplayName("Успешная проверка геттера и сеттера для поля name")
    void testGetAndSetName() {
        Assertions.assertEquals("Oleg", student.getName());
        student.setName("Petr");
        Assertions.assertEquals("Petr", student.getName());
    }

    @Test
    @DisplayName("Корректные оценки добавляются в список оценок")
    public void marksInRange() {
        List<Integer> lst = List.of(2, 3, 4, 5);
        student.addGrade(lst.get(0));
        student.addGrade(lst.get(1));
        student.addGrade(lst.get(2));
        student.addGrade(lst.get(3));
        Assertions.assertEquals(student.getGrades(), lst);
    }

    @Test
    @DisplayName("Добавление неверных оценок кидает исключение")
    public void marksNotInRange() {
        List<Integer> lst = List.of(0, 1, 6, 7);
        Assertions.assertThrows(IllegalArgumentException.class, () -> student.addGrade(lst.get(0)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> student.addGrade(lst.get(1)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> student.addGrade(lst.get(2)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> student.addGrade(lst.get(3)));
    }

    @RepeatedTest(value = 4, name = "Добавление корректной оценки")
    @DisplayName("Добавление корректной оценки")
    void testCorrectGrades(RepetitionInfo repetitionInfo) {
        student.addGrade(repetitionInfo.getCurrentRepetition() + 1);
        Assertions.assertEquals(student.getGrades().get(0), repetitionInfo.getCurrentRepetition() + 1);
    }
}