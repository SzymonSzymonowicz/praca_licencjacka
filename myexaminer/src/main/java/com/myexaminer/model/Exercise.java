package com.myexaminer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Long idExercise;

    @Column(columnDefinition="json")
    private String exerciseBody;

    @ManyToOne
    @JoinColumn(name="fk_exam_id", nullable=false)
    private Exam exam;

    @OneToMany(mappedBy="exercise")
    private List<ArchiveExercise> archiveExercises;
}
