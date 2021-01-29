package com.myexaminer.exerciseTypes;

import java.util.List;

public class ReceivedExercisesWithIdIndividualExam {

    private List<ReceivedExercise> receivedExercises;

    private int idIndividualExam;

    public List<ReceivedExercise> getReceivedExercises() {
        return receivedExercises;
    }

    public void setReceivedExercises(List<ReceivedExercise> receivedExercises) {
        this.receivedExercises = receivedExercises;
    }

    public int getIdIndividualExam() {
        return idIndividualExam;
    }

    public void setIdIndividualExam(int idIndividualExam) {
        this.idIndividualExam = idIndividualExam;
    }
}
