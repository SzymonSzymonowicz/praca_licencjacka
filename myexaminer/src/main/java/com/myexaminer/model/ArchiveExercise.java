package com.myexaminer.model;

import javax.persistence.*;

@Entity
@Table(name = "archive_exercise")
public class ArchiveExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "archive_exercise_id")
    private int idArchiveExercise;

    @ManyToOne
    @JoinColumn(name="fk_exercise_id", nullable=false)
    private Exercise exercise;

    @ManyToOne
    @JoinColumn(name="fk_individual_exam_id", nullable=false)
    private IndividualExam individualExam;

    @Column(name = "gained_points")
    private Integer gainedPoints;

    @Column(name = "answer")
    private String answer;

    @Column(name = "lecturer_comment")
    private String lecturerComment;

    public ArchiveExercise(){}

    public ArchiveExercise(Exercise exercise, IndividualExam individualExam, Integer gainedPoints, String answer, String lecturerComment){
        this.exercise = exercise;
        this.individualExam = individualExam;
        this.gainedPoints = gainedPoints;
        this.answer = answer;
        this.lecturerComment = lecturerComment;
    }

    public int getIdArchiveExercise() {
        return idArchiveExercise;
    }

    public void setIdArchiveExercise(int idArchiveExercise) {
        this.idArchiveExercise = idArchiveExercise;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Integer getGainedPoints() {
        return gainedPoints;
    }

    public void setGainedPoints(Integer gainedPoints) {
        this.gainedPoints = gainedPoints;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getLecturerComment() {
        return lecturerComment;
    }

    public void setLecturerComment(String lecturerComment) {
        this.lecturerComment = lecturerComment;
    }

    public IndividualExam getIndividualExam() {
        return individualExam;
    }

    public void setIndividualExam(IndividualExam individualExam) {
        this.individualExam = individualExam;
    }
}
