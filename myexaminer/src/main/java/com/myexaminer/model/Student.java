package com.myexaminer.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "fk_account_id")
    private int idStudent;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_account_id")
    Account account;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "student_index")
    private String index;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "field_of_study")
    private String fieldOfStudy;

    @ManyToMany(mappedBy = "students")
    private Set<TeachingGroup> teachingGroups = new HashSet<>();

    @OneToMany(mappedBy="student")
    private List<ArchiveExercise> archiveExercises;

    public Student() {
    }

    public Student(int idStudent, String firstName, String lastName, String index, String faculty, String fieldOfStudy) {
        this.idStudent = idStudent;
        this.firstName = firstName;
        this.lastName = lastName;
        this.index = index;
        this.faculty = faculty;
        this.fieldOfStudy = fieldOfStudy;
        this.teachingGroups = new HashSet<>();
        this.archiveExercises = new ArrayList<>();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public Set<TeachingGroup> getTeachingGroups() {
        return teachingGroups;
    }

    public void setTeachingGroups(Set<TeachingGroup> teachingGroups) {
        this.teachingGroups = teachingGroups;
    }

    public void addToTeachingGroups(TeachingGroup teachingGroup) {
        teachingGroups.add(teachingGroup);
    }

    public List<ArchiveExercise> getArchiveExercises() {
        return archiveExercises;
    }

    public void setArchiveExercises(List<ArchiveExercise> archiveExercises) {
        this.archiveExercises = archiveExercises;
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
