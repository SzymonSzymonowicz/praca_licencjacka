package com.myexaminer.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    public int getIdTeachingGroup() {
        return idTeachingGroup;
    }

    public void setIdTeachingGroup(int idTeachingGroup) {
        this.idTeachingGroup = idTeachingGroup;
    }

    public String getTeachingGroupName() {
        return teachingGroupName;
    }

    public void setTeachingGroupName(String teachingGroupName) {
        this.teachingGroupName = teachingGroupName;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public Date getTeachingGroupDateOfStarting() {
        return teachingGroupDateOfStarting;
    }

    public void setTeachingGroupDateOfStarting(Date teachingGroupDateOfStarting) {
        this.teachingGroupDateOfStarting = teachingGroupDateOfStarting;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Set<Student> getUsers() {
        return students;
    }

    public void setUsers(Set<Student> students) {
        this.students = students;
    }

    public void addToUsers(Student student) {
        students.add(student);
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Exam> getExams() {
        return exams;
    }

    public void setExams(Set<Exam> exams) {
        this.exams = exams;
    }

    public TeachingGroup() {
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
