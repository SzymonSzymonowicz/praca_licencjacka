package com.myexaminer.exerciseTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReceivedExercise {
    private Long id;

    private Integer gainedPoints;

    private String lecturerComment;

    private Object answer;

    @Override
    public String toString() {
        return "ReceivedExercise{" +
                "id=" + id +
                ", points=" + gainedPoints +
                ", lecturerComment='" + lecturerComment + '\'' +
                ", answer=" + answer +
                '}';
    }
}
