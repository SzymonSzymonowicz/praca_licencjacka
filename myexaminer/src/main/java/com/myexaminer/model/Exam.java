package com.myexaminer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "exam")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id")
    private int idExam;

    @Column(name = "exam_name")
    private String examName;

    @Column(name = "exam_description")
    private String examDescription;

    @Column(name = "exam_available_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date examAvailableDate;

    @Column(name = "exam_duration_time")
    private Integer examDurationTime;

    public enum Status {
        @JsonProperty("HIDDEN")
        HIDDEN,
        @JsonProperty("OPEN")
        OPEN,
        @JsonProperty("CLOSED")
        CLOSED,
        @JsonProperty("CHECKED")
        CHECKED;
    }

    @Column(name = "exam_status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name="fk_teaching_group_id", nullable=false)
    private TeachingGroup teachingGroup;

    @OneToMany(mappedBy="exam")
    private List<Exercise> exercises;

    @OneToMany(mappedBy="mainExam")
    private List<IndividualExam> individualExams;

    public Exam(String examName, String examDescription, Date examAvailableDate, Integer examDurationTime,
                TeachingGroup teachingGroup, List<Exercise> exercises, Status status) {
        this.examName = examName;
        this.examDescription = examDescription;
        this.examAvailableDate = examAvailableDate;
        this.examDurationTime = examDurationTime;
        this.teachingGroup = teachingGroup;
        this.exercises = exercises;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "idExam=" + idExam +
                ", examName='" + examName + '\'' +
                ", examDescription='" + examDescription + '\'' +
                ", examAvailableDate=" + examAvailableDate +
                ", examDurationTime=" + examDurationTime +
                ", teachingGroup=" + teachingGroup.getIdTeachingGroup() +
                '}';
    }
}
