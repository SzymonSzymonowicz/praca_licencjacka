package com.myexaminer.exerciseTypes;

public class ReceivedExercise {
    private int taskId;

    private int points;

    private String comment;

    private Object answer;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
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

    public ReceivedExercise(int taskId, int points, String comment, Object answer) {
        this.taskId = taskId;
        this.points = points;
        this.comment = comment;
        this.answer = answer;
    }
}
