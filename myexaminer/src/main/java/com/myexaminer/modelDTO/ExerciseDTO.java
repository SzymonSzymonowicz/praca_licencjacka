package com.myexaminer.modelDTO;


import com.myexaminer.model.Exercise;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseDTO {

    private int idExercise;

    private String exerciseBody;

    public ExerciseDTO(Exercise exercise) {
        this.idExercise = exercise.getIdExercise();
        this.exerciseBody = exercise.getExerciseBody();
    }
}
