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

    @Column(name = "fk_lecturer_account_id")
    private int idLecturer;

    @ManyToMany(mappedBy = "teachingGroups")
    private Set<User> users = new HashSet<>();

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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public TeachingGroup() {
    }

    public TeachingGroup(String teachingGroupName, String accessCode, Date teachingGroupDateOfStarting, int idLecturer, Set<User> users) {
        this.teachingGroupName = teachingGroupName;
        this.accessCode = accessCode;
        this.teachingGroupDateOfStarting = teachingGroupDateOfStarting;
        this.idLecturer = idLecturer;
        this.users = users;
    }

    @Override
    public String toString() {
        return "TeachingGroup{" +
                "idTeachingGroup=" + idTeachingGroup +
                ", teachingGroupName='" + teachingGroupName + '\'' +
                ", accessCode='" + accessCode + '\'' +
                ", teachingGroupDateOfStarting=" + teachingGroupDateOfStarting +
                ", idLecturer=" + idLecturer +
                ", users=" + users +
                '}';
    }
}
