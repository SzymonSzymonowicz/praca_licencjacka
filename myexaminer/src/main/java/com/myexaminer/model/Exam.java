package com.myexaminer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myexaminer.modelDTO.ExamDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "exam")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id")
    private Integer idExam;

    @Column(name = "exam_name")
    private String examName;

    @Column(name = "exam_description")
    private String examDescription;

    @Column(name = "exam_available_date")
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

    public void setStatusToHidden(){
        setStatus(Status.HIDDEN);
    }

    public static Exam mapExamDTOToExam(ExamDTO examDTO){
        return Exam.builder()
                .examName(examDTO.getExamName())
                .examAvailableDate(examDTO.getExamAvailableDate())
                .examDescription(examDTO.getExamDescription())
                .examDurationTime(examDTO.getExamDurationTime())
                .build();
    }
}
