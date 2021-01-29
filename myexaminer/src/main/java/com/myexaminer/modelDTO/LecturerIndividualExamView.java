package com.myexaminer.modelDTO;

import java.util.Date;

public class LecturerIndividualExamView {

    private int idIndividualExam;

    private String examName;

    private String examDescription;

    private Date examAvailableDate;

    private int idTeachingGroup;

    private String nameTeachingGroup;

    private String studentIndex;

    public int getIdIndividualExam() {
        return idIndividualExam;
    }

    public void setIdIndividualExam(int idIndividualExam) {
        this.idIndividualExam = idIndividualExam;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamDescription() {
        return examDescription;
    }

    public void setExamDescription(String examDescription) {
        this.examDescription = examDescription;
    }

    public Date getExamAvailableDate() {
        return examAvailableDate;
    }

    public void setExamAvailableDate(Date examAvailableDate) {
        this.examAvailableDate = examAvailableDate;
    }

    public int getIdTeachingGroup() {
        return idTeachingGroup;
    }

    public void setIdTeachingGroup(int idTeachingGroup) {
        this.idTeachingGroup = idTeachingGroup;
    }

    public String getNameTeachingGroup() {
        return nameTeachingGroup;
    }

    public void setNameTeachingGroup(String nameTeachingGroup) {
        this.nameTeachingGroup = nameTeachingGroup;
    }

    public String getStudentIndex() {
        return studentIndex;
    }

    public void setStudentIndex(String studentIndex) {
        this.studentIndex = studentIndex;
    }

    public LecturerIndividualExamView(int idIndividualExam, String examName, String examDescription, Date examAvailableDate, int idTeachingGroup, String nameTeachingGroup, String studentIndex) {
        this.idIndividualExam = idIndividualExam;
        this.examName = examName;
        this.examDescription = examDescription;
        this.examAvailableDate = examAvailableDate;
        this.idTeachingGroup = idTeachingGroup;
        this.nameTeachingGroup = nameTeachingGroup;
        this.studentIndex = studentIndex;
    }
}
