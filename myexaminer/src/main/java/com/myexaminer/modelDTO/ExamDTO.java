package com.myexaminer.modelDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myexaminer.model.Exam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExamDTO {

    private Integer idExam;

    @JsonProperty
    private String examName;

    @JsonProperty
    private String examDescription;

    @JsonProperty
    private String examAvailableDate;

    @JsonProperty
    private Integer examDurationTime;

    private String examStatus;

    public ExamDTO(Exam exam) {
        this.idExam = exam.getIdExam();
        this.examName = exam.getExamName();
        this.examDescription = exam.getExamDescription();
/*        this.examAvailableDate = exam.getExamAvailableDate();*/
        this.examDurationTime = exam.getExamDurationTime();
        this.examStatus = exam.getStatus().name();
    }

}
