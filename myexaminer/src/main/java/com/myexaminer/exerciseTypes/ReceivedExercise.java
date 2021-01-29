package com.myexaminer.exerciseTypes;

public class ReceivedExercise {
    private int idExercise;

    private int gainedPoints;

    private String lecturerComment;

    private Object answer;

    public int getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(int idExercise) {
        this.idExercise = idExercise;
    }

    public int getGainedPoints() {
        return gainedPoints;
    }

    public void setGainedPoints(int gainedPoints) {
        this.gainedPoints = gainedPoints;
    }

    public String getLecturerComment() {
        return lecturerComment;
    }

    public void setLecturerComment(String lecturerComment) {
        this.lecturerComment = lecturerComment;
    }

    public Object getAnswer() {
        return answer;
    }

    public void setAnswer(Object answer) {
        this.answer = answer;
    }

    public ReceivedExercise(int idExercise, int gainedPoints, String comment, Object answer) {
        this.idExercise = idExercise;
        this.gainedPoints = gainedPoints;
        this.lecturerComment = comment;
        this.answer = answer;
    }

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
