package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;

    public Student(String firstName, String lastName, SchoolClass schoolClass) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.schoolClass = schoolClass;
    }
}