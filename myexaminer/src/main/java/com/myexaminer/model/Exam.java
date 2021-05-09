package com.myexaminer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myexaminer.enums.Status;
import com.myexaminer.modelDTO.ExamDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static com.myexaminer.component.DateUtils.parseStringToDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id")
    private Integer idExam;

    private String examName;

    private String description;

    private LocalDateTime availableDate;

    private Integer duration;

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
                ", examDescription='" + description + '\'' +
                ", examAvailableDate=" + availableDate +
                ", examDurationTime=" + duration +
                ", teachingGroup=" + teachingGroup.getIdTeachingGroup() +
                '}';
    }

    public void setStatusToHidden(){
        setStatus(Status.HIDDEN);
    }

    public void setStatusToDraft(){
        setStatus(Status.DRAFT);
    }

    public void setStatusToClosed(){
        setStatus(Status.CLOSED);
    }

    public void setStatusToOpen(){
        setStatus(Status.OPEN);
    }

    public void setStatusToChecked(){
        setStatus(Status.CHECKED);
    }

    public boolean isClosed(){return getStatus().equals(Status.CLOSED);}

    public boolean isOpened(){return getStatus().equals(Status.OPEN);}

    public static Exam mapExamDTOToExam(ExamDTO examDTO){
        return Exam.builder()
                .examName(examDTO.getExamName())
                .availableDate(parseStringToDate(examDTO.getExamAvailableDate()))
                .description(examDTO.getExamDescription())
                .duration(examDTO.getExamDurationTime())
                .build();
    }
}
