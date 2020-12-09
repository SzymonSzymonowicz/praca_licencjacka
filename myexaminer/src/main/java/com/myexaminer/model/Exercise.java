package com.myexaminer.model;

import javax.persistence.*;

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

    public Exercise(String exerciseBody, Exam exam) {
        this.exerciseBody = exerciseBody;
        this.exam = exam;
    }

    public Exercise() {
    }
}
