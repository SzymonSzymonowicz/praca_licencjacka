package com.myexaminer.exerciseTypes;

import java.util.List;

public class ReceivedExercisesWithIdIndividualExam {

    private List<ReceivedExercise> receivedExercises;

    private Integer idIndividualExam;

    private Integer idExam;

    public List<ReceivedExercise> getReceivedExercises() {
        return receivedExercises;
    }

    public void setReceivedExercises(List<ReceivedExercise> receivedExercises) {
        this.receivedExercises = receivedExercises;
    }

    public Integer getIdIndividualExam() {
        return idIndividualExam;
    }

    public void setIdIndividualExam(Integer idIndividualExam) {
        this.idIndividualExam = idIndividualExam;
    }

    public Integer getIdExam() {
        return idExam;
    }

    public void setIdExam(Integer idExam) {
        this.idExam = idExam;
    }
}
