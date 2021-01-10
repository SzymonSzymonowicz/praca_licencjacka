package com.myexaminer.model;

import javax.persistence.*;

@Entity
@Table(name = "exam_status")
public class ExamStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_status_id")
    private int idExamStatus;

    @ManyToOne
    @JoinColumn(name="fk_exam_id", nullable=false)
    private Exam savedExam;

    @ManyToOne
    @JoinColumn(name="fk_teaching_group_id", nullable=false)
    private Exam examTeachingGroup;

    @Column(name = "current_status")
    private String currentStatus;

    public ExamStatus(Exam savedExam, Exam examTeachingGroup, String currentStatus) {
        this.savedExam = savedExam;
        this.examTeachingGroup = examTeachingGroup;
        this.currentStatus = currentStatus;
    }

    public ExamStatus() { }

    public int getIdExamStatus() {
        return idExamStatus;
    }

    public void setIdExamStatus(int idExamStatus) {
        this.idExamStatus = idExamStatus;
    }

    public Exam getSavedExam() {
        return savedExam;
    }

    public void setSavedExam(Exam savedExam) {
        this.savedExam = savedExam;
    }

    public Exam getExamTeachingGroup() {
        return examTeachingGroup;
    }

    public void setExamTeachingGroup(Exam examTeachingGroup) {
        this.examTeachingGroup = examTeachingGroup;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
}
