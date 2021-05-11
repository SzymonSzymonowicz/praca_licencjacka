package com.myexaminer.exerciseTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReceivedExercise {
    private Long idExercise;

    private Integer gainedPoints;

    private String lecturerComment;

    private Object answer;

    @Override
    public String toString() {
        return "ReceivedExercise{" +
                "idExercise=" + idExercise +
                ", points=" + gainedPoints +
                ", lecturerComment='" + lecturerComment + '\'' +
                ", answer=" + answer +
                '}';
    }
}
