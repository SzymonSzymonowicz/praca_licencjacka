package com.myexaminer.modelDTO;


import com.myexaminer.model.Exercise;

public class ExerciseDTO {

    private int idExercise;

    private String exerciseBody;

    public int getIdExercise() {
        return idExercise;
    }

    private void setIdExercise(int idExercise) {
        this.idExercise = idExercise;
    }

    public String getExerciseBody() {
        return exerciseBody;
    }

    private void setExerciseBody(String exerciseBody) {
        this.exerciseBody = exerciseBody;
    }

    public ExerciseDTO(Exercise exercise) {
        this.idExercise = exercise.getIdExercise();
        this.exerciseBody = exercise.getExerciseBody();
    }
}
