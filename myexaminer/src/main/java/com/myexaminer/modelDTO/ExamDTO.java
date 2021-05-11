package com.myexaminer.modelDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myexaminer.model.Exam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static com.myexaminer.component.DateUtils.parseDateToString;

@Getter
@Setter
@AllArgsConstructor
public class ExamDTO {

    private Long idExam;

    @JsonProperty
    private String examName;

    @JsonProperty
    private String examDescription;

    @JsonProperty
    private String examAvailableDate;

    @JsonProperty
    private Long examDurationTime;

    private String examStatus;

    public ExamDTO(Exam exam) {
        this.idExam = exam.getIdExam();
        this.examName = exam.getExamName();
        this.examDescription = exam.getDescription();
        this.examAvailableDate = parseDateToString(exam.getAvailableDate());
        this.examDurationTime = exam.getDuration();
        this.examStatus = exam.getStatus().name();
    }

}
