package com.myexaminer.entity;

import com.myexaminer.dto.ExamDTO;
import com.myexaminer.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    private Long id;

    private String name;

    private String description;

    private LocalDateTime availableFrom;

    private Long duration;

    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne
    @JoinColumn(name = "fk_teaching_group_id", nullable = false)
    private TeachingGroup teachingGroup;

    @OneToMany(mappedBy = "exam")
    private List<Exercise> exercises;

    @OneToMany(mappedBy = "mainExam")
    private List<IndividualExam> individualExams;

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", availableFrom=" + availableFrom +
                ", duration=" + duration +
                ", teachingGroup=" + teachingGroup.getId() +
                '}';
    }

    public void setStateToHidden() {
        setState(State.HIDDEN);
    }

    public void setStateToDraft() {
        setState(State.DRAFT);
    }

    public void setStateToClosed() {
        setState(State.CLOSED);
    }

    public void setStateToOpen() {
        setState(State.OPEN);
    }

    public void setStateToChecked() {
        setState(State.CHECKED);
    }

    public boolean isClosed() {
        return getState().equals(State.CLOSED);
    }

    public boolean isOpen() {
        return getState().equals(State.OPEN);
    }

    public boolean isHidden() {
        return getState().equals(State.HIDDEN);
    }


    public static Exam mapExamDTOToExam(ExamDTO examDTO) {
        return Exam.builder()
                .name(examDTO.getName())
                .availableFrom(parseStringToDate(examDTO.getAvailableFrom()))
                .description(examDTO.getDescription())
                .duration(examDTO.getDuration())
                .build();
    }
}
