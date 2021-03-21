package com.myexaminer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myexaminer.exerciseTypes.ReceivedExercisesWithIdIndividualExam;
import com.myexaminer.modelDTO.ArchiveExerciseDTO;
import com.myexaminer.modelDTO.TwoIdObject;
import com.myexaminer.service.ArchiveExerciseService;
import com.myexaminer.service.CheckingExercisesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    public Iterable<ArchiveExerciseDTO> getExercises(@RequestParam(required = false) Integer idExam, @RequestParam(required = false) Integer idIndExam, HttpServletRequest request) {
        return checkingExercisesService.returnCheckedExercises(idExam, idIndExam, request);
    }

    @PostMapping("/exercises")
    public void createExerciseArchive(@RequestBody TwoIdObject twoIdObject) {
        archiveExerciseService.createExerciseArchive(twoIdObject);
    }

    @PutMapping("/check")
    public void checkExercises(@RequestBody ReceivedExercisesWithIdIndividualExam receivedExercisesWithIdIndividualExam, HttpServletRequest request) throws JsonProcessingException {
        checkingExercisesService.checkExercises(
                receivedExercisesWithIdIndividualExam.getReceivedExercises(),
                receivedExercisesWithIdIndividualExam.getIdIndividualExam(),
                receivedExercisesWithIdIndividualExam.getIdExam(),
                request
        );
    }
}
