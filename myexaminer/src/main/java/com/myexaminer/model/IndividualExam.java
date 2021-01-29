package com.myexaminer.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "individual_exam")
public class IndividualExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "individual_exam_id")
    private int idIndividualExam;

    @ManyToOne
    @JoinColumn(name="fk_exam_id", nullable=false)
    private Exam mainExam;

    @ManyToOne
    @JoinColumn(name="fk_student_id", nullable=false)
    private Student student;

    @OneToMany(mappedBy="individualExam")
    private List<ArchiveExercise> archiveExercises;

    @Column(name = "is_checked")
    private boolean isChecked;

    public int getIdIndividualExam() {
        return idIndividualExam;
    }

    public void setIdIndividualExam(int idIndividualExam) {
        this.idIndividualExam = idIndividualExam;
    }

    public Exam getMainExam() {
        return mainExam;
    }

    public void setMainExam(Exam mainExam) {
        this.mainExam = mainExam;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<ArchiveExercise> getArchiveExercises() {
        return archiveExercises;
    }

    public void setArchiveExercises(List<ArchiveExercise> archiveExercises) {
        this.archiveExercises = archiveExercises;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public IndividualExam(Exam mainExam, Student student) {
        this.mainExam = mainExam;
        this.student = student;
        this.isChecked = false;
        this.archiveExercises = new ArrayList<>();
    }

    public IndividualExam() {
    }
}
