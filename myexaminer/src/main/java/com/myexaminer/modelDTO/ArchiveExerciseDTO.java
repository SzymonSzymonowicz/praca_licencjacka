package com.myexaminer.modelDTO;

import com.myexaminer.model.ArchiveExercise;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArchiveExerciseDTO {
    private Long archivedId;

    private Long exerciseId;

    private Long individualExamId;

    private Integer gainedPoints;

    private String answer;

    private String lecturerComment;

    public ArchiveExerciseDTO(ArchiveExercise archiveExercise) {
        this.archivedId = archiveExercise.getId();
        this.exerciseId = archiveExercise.getExercise().getId();
        this.individualExamId = archiveExercise.getIndividualExam().getId();
        this.gainedPoints = archiveExercise.getGainedPoints();
        this.answer = archiveExercise.getAnswer();
        this.lecturerComment = archiveExercise.getLecturerComment();
    }
}
