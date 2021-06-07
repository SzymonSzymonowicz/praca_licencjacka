package com.myexaminer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myexaminer.entity.Exam;
import com.myexaminer.entity.TeachingGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.myexaminer.component.DateUtils.parseDateToString;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ExamDTO {

    private Long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    @JsonProperty
    private LocalDateTime availableFrom;

    private Long groupId;

    @JsonProperty
    private Long duration;

    private String state;

    public ExamDTO(Exam exam) {
        this.id = exam.getId();
        this.name = exam.getName();
        this.description = exam.getDescription();
        this.availableFrom = exam.getAvailableFrom();
        this.duration = exam.getDuration();
        this.state = exam.getState().name();
        this.groupId = Optional.of(exam.getTeachingGroup())
                .map(TeachingGroup::getId)
                .orElse(null);
    }

}
