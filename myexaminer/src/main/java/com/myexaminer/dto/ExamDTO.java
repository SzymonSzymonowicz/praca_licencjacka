package com.myexaminer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myexaminer.entity.Exam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static com.myexaminer.component.DateUtils.parseDateToString;

@Getter
@Setter
@AllArgsConstructor
public class ExamDTO {

    private Long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    @JsonProperty
    private String availableFrom;

    @JsonProperty
    private Long duration;

    private String state;

    public ExamDTO(Exam exam) {
        this.id = exam.getId();
        this.name = exam.getName();
        this.description = exam.getDescription();
        this.availableFrom = parseDateToString(exam.getAvailableFrom());
        this.duration = exam.getDuration();
        this.state = exam.getState().name();
    }

}
