package com.myexaminer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class IndividualExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="fk_exam_id", nullable=false)
    private Exam mainExam;

    @ManyToOne
    @JoinColumn(name="fk_student_id", nullable=false)
    private Student student;

    @OneToMany(mappedBy="individualExam")
    private List<ArchiveExercise> archiveExercises;

    private boolean isChecked;

    public IndividualExam(Exam mainExam, Student student) {
        this.mainExam = mainExam;
        this.student = student;
        this.isChecked = false;
        this.archiveExercises = new ArrayList<>();
    }
}
