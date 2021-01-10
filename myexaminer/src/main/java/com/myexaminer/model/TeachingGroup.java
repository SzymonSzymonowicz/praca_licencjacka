package com.myexaminer.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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

    @Column(name = "fk_lecturer_account_id")
    private int idLecturer;

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

    @OneToMany(mappedBy="examTeachingGroup")
    private List<ExamStatus> examStatusList;

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

    public int getIdLecturer() {
        return idLecturer;
    }

    public void setIdLecturer(int idLecturer) {
        this.idLecturer = idLecturer;
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

    public List<ExamStatus> getExamStatusList() {
        return examStatusList;
    }

    public void setExamStatusList(List<ExamStatus> examStatusList) {
        this.examStatusList = examStatusList;
    }

    public TeachingGroup() {
    }

    public TeachingGroup(String teachingGroupName, String accessCode, Date teachingGroupDateOfStarting, int idLecturer,
                         Set<Student> students, Set<Exam> exams, List<ExamStatus> examStatusList) {
        this.teachingGroupName = teachingGroupName;
        this.accessCode = accessCode;
        this.teachingGroupDateOfStarting = teachingGroupDateOfStarting;
        this.idLecturer = idLecturer;
        this.students = students;
        this.exams = exams;
        this.examStatusList = examStatusList;
    }

    @Override
    public String toString() {
        return "TeachingGroup{" +
                "idTeachingGroup=" + idTeachingGroup +
                ", teachingGroupName='" + teachingGroupName + '\'' +
                ", accessCode='" + accessCode + '\'' +
                ", teachingGroupDateOfStarting=" + teachingGroupDateOfStarting +
                ", idLecturer=" + idLecturer +
                ", students=" + students +
                ", exams=" + exams +
                '}';
    }
}
