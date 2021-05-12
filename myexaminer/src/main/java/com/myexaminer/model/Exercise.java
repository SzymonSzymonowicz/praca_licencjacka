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
    private Long id;

    @Column(columnDefinition="json")
    private String content;

    @ManyToOne
    @JoinColumn(name="fk_exam_id", nullable=false)
    private Exam exam;

    @OneToMany(mappedBy="exercise")
    private List<ArchiveExercise> archiveExercises;
}
