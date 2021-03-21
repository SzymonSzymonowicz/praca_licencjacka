package com.myexaminer.controller;

import com.myexaminer.modelDTO.LecturerIndividualExamView;
import com.myexaminer.service.IndividualExamService;
import com.myexaminer.service.TeachingGroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "/individual-exams")
public class IndividualExamController {

    private final IndividualExamService individualExamService;

    public IndividualExamController(IndividualExamService individualExamService) {
        this.individualExamService = individualExamService;
    }

    @GetMapping(path = "/group")
    public List<LecturerIndividualExamView> getIndividualsFromLecturerGroup(HttpServletRequest request) {
        return individualExamService.getLecturerIndividualExamViews(request);
    }

}
