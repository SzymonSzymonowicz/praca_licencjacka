package com.myexaminer.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private int idExercise;

    @Column(name = "exercise_body")
    private String exerciseBody;

    @ManyToOne
    @JoinColumn(name="fk_exam_id", nullable=false)
    private Exam exam;

    @OneToMany(mappedBy="exercise")
    private List<ArchiveExercise> archiveExercises;

    public int getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(int idExercise) {
        this.idExercise = idExercise;
    }

    public String getExerciseBody() {
        return exerciseBody;
    }

    public void setExerciseBody(String exerciseBody) {
        this.exerciseBody = exerciseBody;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public List<ArchiveExercise> getArchiveExercises() {
        return archiveExercises;
    }

    public void setArchiveExercises(List<ArchiveExercise> archiveExercises) {
        this.archiveExercises = archiveExercises;
    }

    public Exercise(String exerciseBody, Exam exam, List<ArchiveExercise> archiveExercises) {
        this.exerciseBody = exerciseBody;
        this.exam = exam;
        this.archiveExercises = archiveExercises;
    }

    public Exercise() {
    }
}
