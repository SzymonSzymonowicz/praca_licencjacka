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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class TeachingGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_name")
    private String name;

    private String accessCode;

    private LocalDateTime startingDate;

    @ManyToOne
    @JoinColumn(name = "fk_lecturer_account_id", nullable = false)
    @JsonIgnore
    private Lecturer lecturer;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "student_teaching_group",
            joinColumns = @JoinColumn(name = "fk_teaching_group_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_student_account_id")
    )
    @JsonIgnore
    @Builder.Default
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "teachingGroup")
    @JsonIgnore
    private Set<Exam> exams;

    @OneToMany(mappedBy = "teachingGroup")
    private List<Lesson> lessons;

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }
}
