package com.myexaminer.dto;

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

    private Long individualExamId;

    private String examName;

    private String examDescription;

    private LocalDateTime examAvailableFrom;

    private Long teachingGroupId;

    private String teachingGroupName;

    private String studentIndex;
}
