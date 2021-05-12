package com.myexaminer.exerciseTypes;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReceivedExercisesWithExamInfo {

    private List<ReceivedExercise> receivedExercises;

    private Long individualExamId;

    private Long examId;
}
