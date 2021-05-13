package com.myexaminer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class IndividualExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_exam_id", nullable = false)
    private Exam mainExam;

    @ManyToOne
    @JoinColumn(name = "fk_student_id", nullable = false)
    private Student student;

    @OneToMany(mappedBy = "individualExam")
    private List<ArchiveExercise> archiveExercises;

    private boolean isChecked;

    public IndividualExam(Exam mainExam, Student student) {
        this.mainExam = mainExam;
        this.student = student;
        this.isChecked = false;
        this.archiveExercises = new ArrayList<>();
    }
}
