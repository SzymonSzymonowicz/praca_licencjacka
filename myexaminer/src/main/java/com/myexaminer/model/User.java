package com.myexaminer.model;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "account_idaccount")
    private int idUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idaccount")
    Account account;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "index")
    private String index;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "field_of_study")
    private String fieldOfStudy;

    public User() {
    }

    public User(String firstName, String lastName, String index, String faculty, String fieldOfStudy) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.index = index;
        this.faculty = faculty;
        this.fieldOfStudy = fieldOfStudy;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", index='" + index + '\'' +
                ", faculty='" + faculty + '\'' +
                ", fieldOfStudy='" + fieldOfStudy + '\'' +
                '}';
    }
}
