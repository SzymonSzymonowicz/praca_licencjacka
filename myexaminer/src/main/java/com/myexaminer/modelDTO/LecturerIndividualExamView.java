package com.myexaminer.modelDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LecturerIndividualExamView {

    private int idIndividualExam;

    private String examName;

    private String examDescription;

    private Date examAvailableDate;

    private int idTeachingGroup;

    private String nameTeachingGroup;

    private String studentIndex;
}
