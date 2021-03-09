package com.myexaminer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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
