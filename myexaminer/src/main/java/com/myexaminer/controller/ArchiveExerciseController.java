package com.myexaminer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myexaminer.dto.ArchiveExerciseDTO;
import com.myexaminer.dto.StudentExam;
import com.myexaminer.exerciseTypes.ReceivedExercisesWithExamInfo;
import com.myexaminer.service.ArchiveExerciseService;
import com.myexaminer.service.CheckingExercisesService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/archive")
public class ArchiveExerciseController {

    private final ArchiveExerciseService archiveExerciseService;
    private final CheckingExercisesService checkingExercisesService;

    public ArchiveExerciseController(ArchiveExerciseService archiveExerciseService, CheckingExercisesService checkingExercisesService) {
        this.archiveExerciseService = archiveExerciseService;
        this.checkingExercisesService = checkingExercisesService;
    }

    @GetMapping("/exercises")
    public Iterable<ArchiveExerciseDTO> getExercises(@RequestParam(required = false) Long examId, @RequestParam(required = false) Long individualExamId, Authentication authentication) {
        return checkingExercisesService.returnCheckedExercises(examId, individualExamId, authentication);
    }

    @PostMapping("/exercises")
    public void createExerciseArchive(@RequestBody StudentExam studentExam) {
        archiveExerciseService.createExerciseArchive(studentExam);
    }

    @PutMapping("/check")
    public void checkExercises(@RequestBody ReceivedExercisesWithExamInfo receivedExercisesWithExamInfo, Authentication authentication) throws JsonProcessingException {
        checkingExercisesService.checkExercises(
                receivedExercisesWithExamInfo.getReceivedExercises(),
                receivedExercisesWithExamInfo.getIndividualExamId(),
                receivedExercisesWithExamInfo.getExamId(),
                authentication
        );
    }
}
