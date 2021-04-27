package com.myexaminer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "teaching_group")
public class TeachingGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teaching_group_id")
    private int idTeachingGroup;

    @Column(name = "teaching_group_name")
    private String teachingGroupName;

    @Column(name = "access_code")
    private String accessCode;

    @Column(name = "teaching_group_date_of_starting")
    @Temporal(TemporalType.TIMESTAMP)
    private Date teachingGroupDateOfStarting;

    @ManyToOne
    @JoinColumn(name="fk_lecturer_account_id", nullable=false)
    private Lecturer lecturer;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "student_teaching_group",
            joinColumns = @JoinColumn(name = "fk_teaching_group_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_student_account_id")
    )
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy="teachingGroup")
    private Set<Exam> exams;

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public TeachingGroup(String teachingGroupName, String accessCode, Date teachingGroupDateOfStarting, Lecturer lecturer, Set<Student> students, Set<Exam> exams) {
        this.teachingGroupName = teachingGroupName;
        this.accessCode = accessCode;
        this.teachingGroupDateOfStarting = teachingGroupDateOfStarting;
        this.lecturer = lecturer;
        this.students = students;
        this.exams = exams;
    }
}
