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
    public Exercise getExercise(@RequestBody Map<String, Long> map_id) {
        return exerciseService.getExercise(map_id);
    }

    @PostMapping
    public void addExercise(@RequestBody Exercise exercise) {
        exerciseService.createExercise(exercise);
    }

    @GetMapping("/{id}")
    public Iterable<ExerciseDTO> getAllExercisesById(@PathVariable Long id, HttpServletRequest request) {
        return exerciseService.getExerciseDTOList(id, request);
    }

    @PostMapping("/save")
    public void saveExercises(@RequestBody List<ReceivedExercise> receivedExerciseList) {
        exerciseService.saveExercises(receivedExerciseList);
    }

    @PostMapping("/create/open/{id}")
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    public void createOpenExercise(@RequestBody OpenExercise openExercise,
                                     @PathVariable Long id) throws JsonProcessingException {
        exerciseService.createExerciseTypeO(openExercise, id);
    }

    @PostMapping("/create/closed/{id}")
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    public void createClosedExercise(@RequestBody ClosedExercise closedExercise,
                                   @PathVariable Long id) throws JsonProcessingException {
        exerciseService.createExerciseTypeZ(closedExercise, id);
    }
}
