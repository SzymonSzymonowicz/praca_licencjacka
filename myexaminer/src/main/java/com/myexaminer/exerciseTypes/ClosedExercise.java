package com.myexaminer.exerciseTypes;

import lombok.Getter;

import java.util.List;

@Getter
public class ClosedExercise {

    private String type;

    private String instruction;

    private Integer points;

    private List<String> answers;
}
