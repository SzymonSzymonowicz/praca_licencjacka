package com.myexaminer.modelDTO;

import com.myexaminer.model.Exam;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ExamDTO {

    private int idExam;

    private String examName;

    private String examDescription;

    private Date examAvailableDate;

    private Integer examDurationTime;

    private String examStatus;

    public ExamDTO(Exam exam) {
        this.idExam = exam.getIdExam();
        this.examName = exam.getExamName();
        this.examDescription = exam.getExamDescription();
        this.examAvailableDate = exam.getExamAvailableDate();
        this.examDurationTime = exam.getExamDurationTime();
        this.examStatus = exam.getStatus().name();
    }

}
