package com.myexaminer.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lecturer")
public class Lecturer {

    @Id
    @Column(name = "fk_account_id")
    private int idLecturer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    Account account;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "home_page")
    private String homePage;

    @Column(name = "contact_email")
    private String contactEmail;

    @OneToMany(mappedBy="lecturer")
    private List<TeachingGroup> teachingGroups;

    public int getIdLecturer() {
        return idLecturer;
    }

    public void setIdLecturer(int idLecturer) {
        this.idLecturer = idLecturer;
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

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Lecturer(){

    }

    public Lecturer(Account account, String firstName, String lastName, String homePage, String contactEmail, List<TeachingGroup> teachingGroups) {
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.homePage = homePage;
        this.contactEmail = contactEmail;
        this.teachingGroups = teachingGroups;
    }

    @Override
    public String toString() {
        return "Lecturer{" +
                "idLecturer=" + idLecturer +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", homePage='" + homePage + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                '}';
    }
}
