package com.myexaminer.controller;

import com.myexaminer.modelDTO.LecturerIndividualExamView;
import com.myexaminer.service.IndividualExamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/individual-exams")
public class IndividualExamController {

    private final IndividualExamService individualExamService;

    public IndividualExamController(IndividualExamService individualExamService) {
        this.individualExamService = individualExamService;
    }

    @GetMapping(path = "/group")
    public List<LecturerIndividualExamView> getIndividualsFromLecturerGroup(@RequestBody Long lecturerId) {
        return individualExamService.getLecturerIndividualExamViews(lecturerId);
    }

}
