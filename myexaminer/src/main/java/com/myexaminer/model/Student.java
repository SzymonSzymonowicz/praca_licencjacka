package com.myexaminer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Student {

    @Id
    @Column(name = "fk_account_id")
    private Long idStudent;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_account_id")
    Account account;

    private String firstName;

    private String lastName;

    @Column(name = "student_index")
    private String index;

    private String faculty;

    private String fieldOfStudy;

    @ManyToMany(mappedBy = "students")
    private Set<TeachingGroup> teachingGroups = new HashSet<>();

    @OneToMany(mappedBy="student")
    private List<IndividualExam> individualExams;

    public Student(Long idStudent, String firstName, String lastName, String index, String faculty, String fieldOfStudy) {
        this.idStudent = idStudent;
        this.firstName = firstName;
        this.lastName = lastName;
        this.index = index;
        this.faculty = faculty;
        this.fieldOfStudy = fieldOfStudy;
        this.teachingGroups = new HashSet<>();
    }

    public void addToTeachingGroups(TeachingGroup teachingGroup) {
        teachingGroups.add(teachingGroup);
    }

    @Override
    public String toString() {
        return "Student{" +
                "idStudent=" + idStudent +
                ", account=" + account +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", index='" + index + '\'' +
                ", faculty='" + faculty + '\'' +
                ", fieldOfStudy='" + fieldOfStudy + '\'' +
                ", teachingGroups=" + teachingGroups +
                '}';
    }
}
