package com.myexaminer.modelDTO;

import com.myexaminer.model.ArchiveExercise;

public class ArchiveExerciseDTO {
    private int idArchiveExercise;

    private int idExercise;

    private int idStudent;

    private Integer gainedPoints;

    private String answer;

    private String lecturerComment;

    public int getIdArchiveExercise() {
        return idArchiveExercise;
    }

    public void setIdArchiveExercise(int idArchiveExercise) {
        this.idArchiveExercise = idArchiveExercise;
    }

    public int getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(int idExercise) {
        this.idExercise = idExercise;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public Integer getGainedPoints() {
        return gainedPoints;
    }

    public void setGainedPoints(Integer gainedPoints) {
        this.gainedPoints = gainedPoints;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getLecturerComment() {
        return lecturerComment;
    }

    public void setLecturerComment(String lecturerComment) {
        this.lecturerComment = lecturerComment;
    }

    public ArchiveExerciseDTO(ArchiveExercise archiveExercise) {
        this.idArchiveExercise = archiveExercise.getIdArchiveExercise();
        this.idExercise = archiveExercise.getExercise().getIdExercise();
        this.idStudent = archiveExercise.getStudent().getIdStudent();
        this.gainedPoints = archiveExercise.getGainedPoints();
        this.answer = archiveExercise.getAnswer();
        this.lecturerComment = archiveExercise.getLecturerComment();
    }
}
