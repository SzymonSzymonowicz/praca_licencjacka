package com.myexaminer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ArchiveExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_exercise_id", nullable = false)
    private Exercise exercise;

    @ManyToOne
    @JoinColumn(name = "fk_individual_exam_id", nullable = false)
    private IndividualExam individualExam;

    private Integer gainedPoints;

    @Column(columnDefinition = "json")
    private String answer;

    private String lecturerComment;

    public ArchiveExercise(Exercise exercise, IndividualExam individualExam, Integer gainedPoints, String answer, String lecturerComment) {
        this.exercise = exercise;
        this.individualExam = individualExam;
        this.gainedPoints = gainedPoints;
        this.answer = answer;
        this.lecturerComment = lecturerComment;
    }
}
