package com.myexaminer.exerciseTypes;

import java.util.List;

public class ReceivedExercisesWithStudentEmail {

    private List<ReceivedExercise> receivedExercises;

    private String studentEmail;

    public List<ReceivedExercise> getReceivedExercises() {
        return receivedExercises;
    }

    public void setReceivedExercises(List<ReceivedExercise> receivedExercises) {
        this.receivedExercises = receivedExercises;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
}
