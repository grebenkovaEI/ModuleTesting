package ru.stepup.testing;

public class Main {
    public static void main(String[] args) {
        Student student = new Student("Oleg");
        student.addGrade(5);
        student.getGrades().add(-20);
        System.out.println(student);
    }
}
