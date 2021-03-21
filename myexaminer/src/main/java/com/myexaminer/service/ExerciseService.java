package com.myexaminer.service;

import com.myexaminer.exerciseTypes.ReceivedExercise;
import com.myexaminer.model.Exercise;
import com.myexaminer.modelDTO.ExerciseDTO;
import com.myexaminer.repository.ExerciseRepository;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private IndividualExamService individualExamService;

    public ExerciseService(ExerciseRepository exerciseRepository, IndividualExamService individualExamService) {
        this.exerciseRepository = exerciseRepository;
        this.individualExamService = individualExamService;
    }

    public void exerciseSave(Exercise exercise) {
        exerciseRepository.save(exercise);
    }

    public boolean exerciseExistsById(int idExercise) {
        Optional<Exercise> exerciseById = exerciseRepository.findByIdExercise(idExercise);

        return exerciseById.isPresent();
    }

    public Exercise returnExerciseById(int idExercise) {
        Optional<Exercise> exerciseById = exerciseRepository.findByIdExercise(idExercise);

        return exerciseById.orElseThrow(() -> new NoSuchElementException("There is no Exercise in database that you were looking for."));
    }

    public Iterable<Exercise> returnAllExercises() {
        return exerciseRepository.findAll();
    }

    public String getExerciseType(int idExercise) {
        JSONObject obj = new JSONObject(
                returnExerciseById(idExercise).getExerciseBody());
        return obj.getString("type");
    }

    public Exercise getExercise(Map<String, Integer> map_idExercise) {
        Integer idExercise = map_idExercise.get("idExercise");
        if (!exerciseExistsById(idExercise)) {
            log.info("Exercise with given ID -> {} <- DOES NOT EXIST", idExercise);
            return null;
        }

        Exercise returnedExercise = returnExerciseById(idExercise);

        log.info("Exercise with ID -> {} <- HAS BEEN RETURNED", returnedExercise.getIdExercise());

        return returnedExercise;
    }

    public void createExercise(Exercise exercise) {
        if (exerciseExistsById(exercise.getIdExercise())) {
            log.info("Exercise with given ID -> {} <- ALREADY EXISTS", exercise.getIdExercise());
            return;
        }

        exerciseSave(exercise);
        log.info("Exercise with ID -> {} <- has been ADDED", exercise.getIdExercise());
    }

    public List<ExerciseDTO> getExerciseDTOList(Integer idExam, HttpServletRequest request) {
        if (request.getUserPrincipal().getName().equals("dianaLektor@gmail.com")) {
            return StreamSupport.stream(returnAllExercises().spliterator(), false).
                    filter(exercise -> exercise.getExam().getIdExam() == individualExamService.returnIndividualExamById(idExam).getMainExam().getIdExam()).
                    map(exercise -> new ExerciseDTO(exercise)).collect(Collectors.toList());
        } else {
            return StreamSupport.stream(returnAllExercises().spliterator(), false).
                    filter(exercise -> exercise.getExam().getIdExam() == idExam).
                    map(exercise -> new ExerciseDTO(exercise)).collect(Collectors.toList());
        }
    }

    public void saveExercises(List<ReceivedExercise> receivedExerciseList) {
        for (ReceivedExercise exercise : receivedExerciseList) {
            String type = getExerciseType(exercise.getIdExercise());
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
    }
}
