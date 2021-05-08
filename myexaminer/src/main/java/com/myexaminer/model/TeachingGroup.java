package com.myexaminer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "teaching_group")
public class TeachingGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teaching_group_id")
    private int idTeachingGroup;

    @Column(name = "teaching_group_name")
    private String name;

    @Column(name = "access_code")
    private String accessCode;

    @Column(name = "teaching_group_date_of_starting")
    private LocalDateTime teachingGroupDateOfStarting;

    @ManyToOne
    @JoinColumn(name="fk_lecturer_account_id", nullable=false)
    @JsonIgnore
    private Lecturer lecturer;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "student_teaching_group",
            joinColumns = @JoinColumn(name = "fk_teaching_group_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_student_account_id")
    )
    @JsonIgnore
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy="teachingGroup")
    @JsonIgnore
    private Set<Exam> exams;

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }
}
