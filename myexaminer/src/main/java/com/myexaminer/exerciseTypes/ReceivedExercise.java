package com.myexaminer.exerciseTypes;

public class ReceivedExercise {
    private int idExercise;

    private int points;

    private String comment;

    private Object answer;

    public int getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(int idExercise) {
        this.idExercise = idExercise;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Object getAnswer() {
        return answer;
    }

    public void setAnswer(Object answer) {
        this.answer = answer;
    }

    public ReceivedExercise(int idExercise, int points, String comment, Object answer) {
        this.idExercise = idExercise;
        this.points = points;
        this.comment = comment;
        this.answer = answer;
    }
}
