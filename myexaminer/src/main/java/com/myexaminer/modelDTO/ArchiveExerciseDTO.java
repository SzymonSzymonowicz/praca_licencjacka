package com.myexaminer.modelDTO;

import com.myexaminer.model.ArchiveExercise;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArchiveExerciseDTO {
    private int idArchiveExercise;

    private int idExercise;

    private int idIndividualExam;

    private Integer gainedPoints;

    private String answer;

    private String lecturerComment;

    public ArchiveExerciseDTO(ArchiveExercise archiveExercise) {
        this.idArchiveExercise = archiveExercise.getIdArchiveExercise();
        this.idExercise = archiveExercise.getExercise().getIdExercise();
        this.idIndividualExam = archiveExercise.getIndividualExam().getIdIndividualExam();
        this.gainedPoints = archiveExercise.getGainedPoints();
        this.answer = archiveExercise.getAnswer();
        this.lecturerComment = archiveExercise.getLecturerComment();
    }
}
