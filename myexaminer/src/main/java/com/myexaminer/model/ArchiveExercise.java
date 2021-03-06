package com.myexaminer.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "archive_exercise")
public class ArchiveExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "archive_exercise_id")
    private int idArchiveExercise;

    @ManyToOne
    @JoinColumn(name="fk_exercise_id", nullable=false)
    private Exercise exercise;

    @ManyToOne
    @JoinColumn(name="fk_individual_exam_id", nullable=false)
    private IndividualExam individualExam;

    @Column(name = "gained_points")
    private Integer gainedPoints;

    @Column(name = "answer")
    private String answer;

    @Column(name = "lecturer_comment")
    private String lecturerComment;

    public ArchiveExercise(Exercise exercise, IndividualExam individualExam, Integer gainedPoints, String answer, String lecturerComment){
        this.exercise = exercise;
        this.individualExam = individualExam;
        this.gainedPoints = gainedPoints;
        this.answer = answer;
        this.lecturerComment = lecturerComment;
    }
}
