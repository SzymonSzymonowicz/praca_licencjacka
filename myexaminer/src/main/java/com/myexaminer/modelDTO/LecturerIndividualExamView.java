package com.myexaminer.modelDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LecturerIndividualExamView {

    private Long idIndividualExam;

    private String examName;

    private String examDescription;

    private LocalDateTime examAvailableDate;

    private Long idTeachingGroup;

    private String nameTeachingGroup;

    private String studentIndex;
}
