package com.myexaminer.dto;

import com.myexaminer.entity.Exam;
import com.myexaminer.entity.TeachingGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class ExamDTO {

    private Long id;

    private String name;

    private String description;

    private LocalDateTime availableFrom;

    private Long groupId;

    private String groupName;

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
        this.groupName = Optional.of(exam.getTeachingGroup())
                .map(TeachingGroup::getName)
                .orElse(null);
    }

}
