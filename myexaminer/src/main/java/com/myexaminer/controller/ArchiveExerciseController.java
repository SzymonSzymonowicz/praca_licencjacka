package com.myexaminer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myexaminer.exerciseTypes.ReceivedExercisesWithIdIndividualExam;
import com.myexaminer.model.*;
import com.myexaminer.modelDTO.ArchiveExerciseDTO;
import com.myexaminer.modelDTO.TwoIdObject;
import com.myexaminer.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path="/archive")
public class ArchiveExerciseController {

    private final ArchiveExerciseService archiveExerciseService;
    private final ExamService examService;
    private final IndividualExamService individualExamService;
    private final CheckingExercisesService checkingExercisesService;

    public ArchiveExerciseController(ArchiveExerciseService archiveExerciseService, CheckingExercisesService checkingExercisesService,
                                     ExamService examService, IndividualExamService individualExamService){
        this.archiveExerciseService = archiveExerciseService;
        this.examService = examService;
        this.individualExamService = individualExamService;
        this.checkingExercisesService = checkingExercisesService;
    }

    @PostMapping("/createExerciseArchive")
    public ResponseEntity<HttpStatus> createExerciseArchive(@RequestBody TwoIdObject twoIdObject) {
        int idStudent = twoIdObject.getIdStudent();
        int idExam = twoIdObject.getIdExam();

        IndividualExam individualExam = individualExamService
                .createOrGetIndividualExamAndReturn(idStudent, idExam);

        if(archiveExerciseService.doArchiveExerciseExists(
                examService.returnExamById(idExam).getExercises().get(0).getIdExercise(),
                individualExam.getIdIndividualExam())){
            return ResponseEntity.ok(HttpStatus.CONFLICT);
        } else {
            archiveExerciseService.createNewArchiveExercises(examService.returnExamById(idExam).getExercises(), individualExam);
            return ResponseEntity.ok(HttpStatus.OK);
        }
    }

    @PutMapping("/checkExercises")
    public ResponseEntity<HttpStatus> checkExercises(@RequestBody ReceivedExercisesWithIdIndividualExam receivedExercisesWithIdIndividualExam, HttpServletRequest request) throws JsonProcessingException {

        checkingExercisesService.checkExercises(
                receivedExercisesWithIdIndividualExam.getReceivedExercises(),
                receivedExercisesWithIdIndividualExam.getIdIndividualExam(),
                receivedExercisesWithIdIndividualExam.getIdExam(),
                request
                );

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/getExercises")
    public @ResponseBody Iterable<ArchiveExerciseDTO> getExercises(@RequestParam(required = false) Integer idExam, @RequestParam(required = false) Integer idIndExam, HttpServletRequest request){
        return checkingExercisesService.returnCheckedExercises(idExam, idIndExam, request);
    }
}
