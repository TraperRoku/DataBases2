package org.example.db;

import org.example.entity.*;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class SampleDataCreator {
    private final EntityManager em;
    private final List<SchoolClass> classes = new ArrayList<>();
    private final List<Student> students = new ArrayList<>();
    private final List<Teacher> teachers = new ArrayList<>();
    private final List<Subject> subjects = new ArrayList<>();

    public SampleDataCreator(EntityManager em) {
        this.em = em;
    }

    public void createSampleData() {
        em.getTransaction().begin();

        Teacher teacher1 = new Teacher("Anna", "Nowak");
        Teacher teacher2 = new Teacher("Jan", "Kowalski");
        Teacher teacher3 = new Teacher("Maria", "Wiśniewska");

        em.persist(teacher1);
        em.persist(teacher2);
        em.persist(teacher3);
        teachers.addAll(List.of(teacher1, teacher2, teacher3));

        SchoolClass class1 = new SchoolClass("1A", "2023");
        SchoolClass class2 = new SchoolClass("2B", "2023");

        class1.setHomeroomTeacher(teacher1);
        class2.setHomeroomTeacher(teacher2);

        em.persist(class1);
        em.persist(class2);
        classes.addAll(List.of(class1, class2));

        students.add(createStudent("Adam", "Nowak", class1));
        students.add(createStudent("Ewa", "Kowalska", class1));
        students.add(createStudent("Piotr", "Wiśniewski", class1));
        students.add(createStudent("Katarzyna", "Wójcik", class1));

        students.add(createStudent("Michał", "Kamiński", class2));
        students.add(createStudent("Agnieszka", "Lewandowska", class2));
        students.add(createStudent("Tomasz", "Zieliński", class2));
        students.add(createStudent("Magdalena", "Szymańska", class2));

        subjects.add(createSubject("Matematyka", teacher1, class1));
        subjects.add(createSubject("Fizyka", teacher2, class1));
        subjects.add(createSubject("Chemia", teacher3, class1));

        subjects.add(createSubject("Język polski", teacher2, class2));
        subjects.add(createSubject("Historia", teacher3, class2));
        subjects.add(createSubject("Biologia", teacher1, class2));

        em.getTransaction().commit();
    }

    private Student createStudent(String firstName, String lastName, SchoolClass schoolClass) {
        Student student = new Student(firstName, lastName, schoolClass);
        em.persist(student);
        return student;
    }

    private Subject createSubject(String name, Teacher teacher, SchoolClass schoolClass) {
        Subject subject = new Subject(name, teacher, schoolClass);
        em.persist(subject);
        return subject;
    }

    public List<Student> getSampleStudents() {
        return students;
    }

    public List<Teacher> getSampleTeachers() {
        return teachers;
    }
}