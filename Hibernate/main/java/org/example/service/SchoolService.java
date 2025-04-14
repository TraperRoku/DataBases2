package org.example.service;

import org.example.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class SchoolService {
    private final EntityManager em;

    public SchoolService(EntityManager em) {
        this.em = em;
    }

    public void displayStudentSubjects(Student student) {
        System.out.println("Uczeń: " + student.getFirstName() + " " + student.getLastName());
        System.out.println("Klasa: " + student.getSchoolClass().getName());
        System.out.println("Przedmioty:");

        TypedQuery<Subject> query = em.createQuery(
                "SELECT s FROM Subject s WHERE s.schoolClass = :class", Subject.class);
        query.setParameter("class", student.getSchoolClass());

        query.getResultList().forEach(subject -> {
            System.out.println("- " + subject.getName() +
                    " (nauczyciel: " + subject.getTeacher().getFirstName() +
                    " " + subject.getTeacher().getLastName() + ")");
        });
    }

    public void displayTeacherStudents(Teacher teacher) {
        System.out.println("\nNauczyciel: " + teacher.getFirstName() + " " + teacher.getLastName());

        if (teacher.getHomeroomClass() != null) {
            System.out.println("Wychowawca klasy: " + teacher.getHomeroomClass().getName());
            System.out.println("Uczniowie:");
            teacher.getHomeroomClass().getStudents().forEach(student -> {
                System.out.println("- " + student.getFirstName() + " " + student.getLastName());
            });
        }

        List<Subject> subjects = em.createQuery(
                        "SELECT s FROM Subject s WHERE s.teacher = :teacher", Subject.class)
                .setParameter("teacher", teacher)
                .getResultList();

        if (!subjects.isEmpty()) {
            System.out.println("\nUczniowie z przedmiotów:");
            subjects.forEach(subject -> {
                System.out.println("Przedmiot: " + subject.getName() +
                        " (klasa: " + subject.getSchoolClass().getName() + ")");

            });
        }
    }
}