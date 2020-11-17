package com.myexaminer.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "teaching_group")
public class TeachingGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idteaching_group")
    private int idTeachingGroup;

    @Column(name = "teaching_group_name")
    private String teachingGroupName;

    @Column(name = "access_code")
    private String accessCode;

    @Column(name = "teaching_group_date_of_starting")
    @Temporal(TemporalType.TIMESTAMP)
    private Date teachingGroupDateOfStarting;

    @Column(name = "lecturer_account_idaccount")
    private int teachingGroupIdLecturer;

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

    public int getTeachingGroupIdLecturer() {
        return teachingGroupIdLecturer;
    }

    public void setTeachingGroupIdLecturer(int teachingGroupIdLecturer) {
        this.teachingGroupIdLecturer = teachingGroupIdLecturer;
    }

    public TeachingGroup() {
    }

    public TeachingGroup(String teachingGroupName, String accessCode, Date teachingGroupDateOfStarting, int teachingGroupIdLecturer) {
        this.teachingGroupName = teachingGroupName;
        this.accessCode = accessCode;
        this.teachingGroupDateOfStarting = teachingGroupDateOfStarting;
        this.teachingGroupIdLecturer = teachingGroupIdLecturer;
    }

    @Override
    public String toString() {
        return "TeachingGroup{" +
                "idTeachingGroup=" + idTeachingGroup +
                ", teachingGroupName='" + teachingGroupName + '\'' +
                ", accessCode='" + accessCode + '\'' +
                ", teachingGroupDateOfStarting=" + teachingGroupDateOfStarting +
                ", teachingGroupIdLecturer=" + teachingGroupIdLecturer +
                '}';
    }
}
