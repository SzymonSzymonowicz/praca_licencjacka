package com.myexaminer.exerciseTypes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlanksExercise extends OpenExercise{
    private String fill;

    public BlanksExercise(String type, String instruction, Long points, String fill) {
        super(type, instruction, points);
        this.fill = fill;
    }

    @Override
    public String toString() {
        return "BlanksExercise{" +
                "fill='" + fill + '\'' +
                ", type='" + type + '\'' +
                ", instruction='" + instruction + '\'' +
                ", points=" + points +
                '}';
    }
}
