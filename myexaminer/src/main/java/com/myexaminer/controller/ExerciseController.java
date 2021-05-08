package com.myexaminer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myexaminer.exerciseTypes.ClosedExercise;
import com.myexaminer.exerciseTypes.OpenExercise;
import com.myexaminer.exerciseTypes.ReceivedExercise;
import com.myexaminer.model.Exercise;
import com.myexaminer.modelDTO.ExerciseDTO;
import com.myexaminer.service.ExerciseService;
import com.myexaminer.service.IndividualExamService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping(path = "/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public Exercise getExercise(@RequestBody Map<String, Integer> map_idExercise) {
        return exerciseService.getExercise(map_idExercise);
    }

    @PostMapping
    public void addExercise(@RequestBody Exercise exercise) {
        exerciseService.createExercise(exercise);
    }

    @GetMapping("/{idExam}")
    public Iterable<ExerciseDTO> getAllExercisesByIdExam(@PathVariable Integer idExam, HttpServletRequest request) {
        return exerciseService.getExerciseDTOList(idExam, request);
    }

    @PostMapping("/save")
    public void saveExercises(@RequestBody List<ReceivedExercise> receivedExerciseList) {
        exerciseService.saveExercises(receivedExerciseList);
    }

    @PostMapping("/create/open/{idExam}")
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    public void createOpenExercise(@RequestBody OpenExercise openExercise,
                                     @PathVariable Integer idExam) throws JsonProcessingException {
        exerciseService.createExerciseTypeO(openExercise, idExam);
    }

    @PostMapping("/create/closed/{idExam}")
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    public void createClosedExercise(@RequestBody ClosedExercise closedExercise,
                                   @PathVariable Integer idExam) throws JsonProcessingException {
        exerciseService.createExerciseTypeZ(closedExercise, idExam);
    }
}
