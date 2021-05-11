package com.myexaminer.controller;

import com.myexaminer.enums.Status;
import com.myexaminer.model.Exam;
import com.myexaminer.modelDTO.ExamDTO;
import com.myexaminer.modelDTO.GenericOneValue;
import com.myexaminer.modelDTO.GenericTwoValues;
import com.myexaminer.service.ExamService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping(path = "/exam")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    public Exam getExam(@RequestBody Map<String, Long> map_idExam) {
        return examService.getExam(map_idExam);
    }

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @PostMapping("/{idGroup}")
    public void addExam(@RequestBody ExamDTO examDTO, @PathVariable Long idGroup) throws ParseException {
        examService.createExam(examDTO, idGroup);
    }

    @GetMapping("/{idGroup}")
    public Iterable<ExamDTO> getAllExamsByIdGroup(@PathVariable Long idGroup) {
        return examService.getExamDTOSByIdGroup(idGroup);
    }

    @GetMapping("/status")
    public Status getExamStatus(@RequestBody GenericOneValue idExam) {
        return examService.getStatus(idExam);
    }

    @PutMapping("/status")
    public void changeExamStatus(@RequestBody GenericTwoValues genericTwoValues) {
        examService.updateExamStatus(genericTwoValues);
    }
}
