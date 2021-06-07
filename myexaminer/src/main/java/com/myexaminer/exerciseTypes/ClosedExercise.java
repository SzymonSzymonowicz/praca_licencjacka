package com.myexaminer.exerciseTypes;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ClosedExercise extends OpenExercise {
    private List<String> answers;

    public ClosedExercise(String type, String instruction, Long points, List<Answer> answers) {
        super(type, instruction, points);
        this.answers = getStringAnswerList(answers);
    }

    private List<String> getStringAnswerList(List<Answer> answers) {
        return answers.stream()
                .map(Answer::toString)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "ClosedExercise{" +
                "answers=" + answers +
                ", type='" + type + '\'' +
                ", instruction='" + instruction + '\'' +
                ", points=" + points +
                '}';
    }
}
