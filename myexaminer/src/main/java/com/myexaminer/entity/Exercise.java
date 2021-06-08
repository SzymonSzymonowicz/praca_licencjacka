package com.myexaminer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    @Column(columnDefinition = "json")
    private String content;

    @ManyToOne
    @JoinColumn(name = "fk_exam_id", nullable = false)
    @JsonIgnore
    private Exam exam;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    // TODO check if ignoring this field didn't broke anything in frontend, if so then ignore excercise in archive
    @JsonIgnore
    private List<ArchiveExercise> archiveExercises;
}
