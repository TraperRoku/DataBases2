package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.db.SampleDataCreator;
import org.example.service.SchoolService;

public class SchoolApp {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SchoolAppPU");
        EntityManager em = emf.createEntityManager();

        try {
            SampleDataCreator dataCreator = new SampleDataCreator(em);
            dataCreator.createSampleData();

            SchoolService schoolService = new SchoolService(em);

            System.out.println("=== Przedmioty uczniów ===");
            dataCreator.getSampleStudents().forEach(student -> {
                schoolService.displayStudentSubjects(student);
                System.out.println();
            });

            System.out.println("\n=== Uczniowie nauczycieli ===");
            dataCreator.getSampleTeachers().forEach(teacher -> {
                schoolService.displayTeacherStudents(teacher);
                System.out.println();
            });

        } finally {
            em.close();
            emf.close();
        }
    }
}