package com.myexaminer.controller;

import com.myexaminer.modelDTO.LecturerIndividualExamView;
import com.myexaminer.service.IndividualExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/individual-exams")
@RequiredArgsConstructor
public class IndividualExamController {

    private final IndividualExamService individualExamService;

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @GetMapping("/lecturer-groups")
    public List<LecturerIndividualExamView> getIndividualsFromLecturerGroup(Authentication authentication) {
        return individualExamService.getLecturerIndividualExamViews(authentication);
    }
}
