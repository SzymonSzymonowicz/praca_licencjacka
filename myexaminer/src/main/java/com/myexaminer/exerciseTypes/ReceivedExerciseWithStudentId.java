package com.myexaminer.exerciseTypes;

import java.util.List;

public class ReceivedExerciseWithStudentId {

    private List<ReceivedExercise> receivedExercises;

    private int idStudent;

    public List<ReceivedExercise> getReceivedExercises() {
        return receivedExercises;
    }

    public void setReceivedExercises(List<ReceivedExercise> receivedExercises) {
        this.receivedExercises = receivedExercises;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }
}
