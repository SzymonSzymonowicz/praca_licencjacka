package com.myexaminer.controller;

import com.myexaminer.model.Exam;
import com.myexaminer.modelDTO.ExamDTO;
import com.myexaminer.modelDTO.GenericOneValue;
import com.myexaminer.modelDTO.GenericTwoValues;
import com.myexaminer.service.ExamService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Controller
@RequestMapping(path="/exam")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    public @ResponseBody Exam getExam (@RequestBody Map<String, Integer> map_idExam) {
        Integer idExam = map_idExam.get("idExam");
        if(!examService.examExistsById(idExam)){
            log.info("Exam with given ID -> {} <- DOES NOT EXIST", idExam);
            return null;
        }

        Exam returnedExam = examService.returnExamById(idExam);

        log.info("Exam with ID -> {} <- HAS BEEN RETURNED", returnedExam.getIdExam());

        return returnedExam;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addExam(@RequestBody Exam exam) {
        if(examService.examExistsById(exam.getIdExam())){
            log.info("Exam with given ID -> {} <- ALREADY EXISTS", exam.getIdExam());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        examService.examSave(exam);
        log.info("Exam with ID -> {} <- has been ADDED", exam.getIdExam());

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{idGroup}")
    public @ResponseBody Iterable<ExamDTO> getAllExamsByIdGroup(@PathVariable int idGroup) {
        return StreamSupport.stream(examService.returnAllExams().spliterator(), false).
                filter(exam -> exam.getTeachingGroup().getIdTeachingGroup() == idGroup).
                map(exam -> new ExamDTO(exam)).collect(Collectors.toList());
    }

    @GetMapping("/status")
    public @ResponseBody Exam.Status getExamStatus(@RequestBody GenericOneValue idExam){
        return examService.returnExamById((Integer) idExam.getFirstValue()).getStatus();
    }

    @PutMapping("/status")
    public ResponseEntity<HttpStatus> changeExamStatus(@RequestBody GenericTwoValues genericTwoValues){
        Integer idExam = (Integer) genericTwoValues.getFirstValue();
        Exam.Status status = Exam.Status.valueOf((String) genericTwoValues.getSecondValue());

        Exam exam = examService.returnExamById(idExam);
        exam.setStatus(status);

        examService.examSave(exam);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
