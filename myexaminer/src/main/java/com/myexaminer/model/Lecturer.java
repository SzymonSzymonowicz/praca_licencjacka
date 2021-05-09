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
public class Lecturer {

    @Id
    @Column(name = "fk_account_id")
    private int idLecturer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_account_id")
    Account account;

    private String firstName;

    private String lastName;

    private String homePage;

    private String contactEmail;

    @OneToMany(mappedBy="lecturer")
    private List<TeachingGroup> teachingGroups;

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
