package com.myexaminer.modelDTO;


import com.myexaminer.model.Exercise;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseDTO {

    private Long id;

    private String content;

    public ExerciseDTO(Exercise exercise) {
        this.id = exercise.getId();
        this.content = exercise.getContent();
    }
}
