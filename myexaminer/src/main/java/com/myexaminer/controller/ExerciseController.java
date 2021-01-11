package com.myexaminer.controller;

import com.myexaminer.exerciseTypes.ReceivedExercise;
import com.myexaminer.model.Exercise;
import com.myexaminer.modelDTO.ExerciseDTO;
import com.myexaminer.service.ExerciseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Controller
@RequestMapping(path="/exercise")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addNewExercise (@RequestBody Exercise exercise) {
        if(exerciseService.exerciseExistsById(exercise.getIdExercise())){
            log.info("Exercise with given ID -> {} <- ALREADY EXISTS", exercise.getIdExercise());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        exerciseService.exerciseSave(exercise);
        log.info("Exercise with ID -> {} <- has been ADDED", exercise.getIdExercise());

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public @ResponseBody Exercise getExercise (@RequestBody Map<String, Integer> map_idExercise) {
        Integer idExercise = map_idExercise.get("idExercise");
        if(!exerciseService.exerciseExistsById(idExercise)){
            log.info("Exercise with given ID -> {} <- DOES NOT EXIST", idExercise);
            return null;
        }

        Exercise returnedExercise = exerciseService.returnExerciseById(idExercise);

        log.info("Exercise with ID -> {} <- HAS BEEN RETURNED", returnedExercise.getIdExercise());

        return returnedExercise;
    }

    @GetMapping("/{idExam}")
    public @ResponseBody Iterable<ExerciseDTO> getAllExercisesByIdExam(@PathVariable int idExam) {
        return StreamSupport.stream(exerciseService.returnAllExercises().spliterator(), false).
                filter(exercise -> exercise.getExam().getIdExam() == idExam).
                map(exercise -> new ExerciseDTO(exercise)).collect(Collectors.toList());
    }

    @PostMapping("/saveExercises")
    public ResponseEntity<HttpStatus> saveExercises(@RequestBody List<ReceivedExercise> receivedExerciseList){
        for(ReceivedExercise exercise: receivedExerciseList){
            String type = exerciseService.getExerciseType(exercise.getIdExercise());
            switch (type) {
                case "L":
                    System.out.println(exercise.getAnswer());
                    break;
                case "Z": {
                    String x = (String) exercise.getAnswer();
                    System.out.println(x.split(", ")[1]);
                    break;
                }
                case "O": {
                    String x = (String) exercise.getAnswer();
                    System.out.println(x);
                    break;
                }
            }
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

}
