package com.myexaminer.exerciseTypes;

import lombok.Getter;

@Getter
public class Answer {
    private String text;
    private String value;

    @Override
    public String toString() {
        return text + "," + value;
    }
}
