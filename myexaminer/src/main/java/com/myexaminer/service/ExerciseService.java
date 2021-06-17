package com.myexaminer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myexaminer.dto.ExerciseDTO;
import com.myexaminer.entity.Exam;
import com.myexaminer.entity.Exercise;
import com.myexaminer.exerciseTypes.OpenExercise;
import com.myexaminer.exerciseTypes.ReceivedExercise;
import com.myexaminer.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final IndividualExamService individualExamService;
    private final ExamService examService;

    public void saveExercise(Exercise exercise) {
        exerciseRepository.save(exercise);
    }

    public boolean exerciseExistsById(Long id) {
        Optional<Exercise> exerciseById = exerciseRepository.findById(id);

        return exerciseById.isPresent();
    }

    public Exercise getExerciseById(Long id) {
        Optional<Exercise> exerciseById = exerciseRepository.findById(id);

        return exerciseById.orElseThrow(() -> new EntityNotFoundException("There is no Exercise in database that you were looking for."));
    }

    public Iterable<Exercise> returnAllExercises() {
        return exerciseRepository.findAll();
    }

    public String getExerciseType(Long id) {
        JSONObject obj = new JSONObject(
                getExerciseById(id).getContent());
        System.out.println(obj);
        return obj.getString("type");
    }

    public void createExercise(Exercise exercise) {
        if (exerciseExistsById(exercise.getId())) {
            log.info("Exercise with given ID -> {} <- ALREADY EXISTS", exercise.getId());
            return;
        }

        saveExercise(exercise);
        log.info("Exercise with ID -> {} <- has been ADDED", exercise.getId());
    }

    public List<ExerciseDTO> getExerciseDTOList(Long id, HttpServletRequest request) {
        if (request.getUserPrincipal().getName().equals("dianaLektor@gmail.com")) {
            return StreamSupport.stream(returnAllExercises().spliterator(), false).
                    filter(exercise -> exercise.getExam().getId() == individualExamService.getIndividualExamById(id).getMainExam().getId()).
                    map(exercise -> new ExerciseDTO(exercise)).collect(Collectors.toList());
        } else {
            return StreamSupport.stream(returnAllExercises().spliterator(), false).
                    filter(exercise -> exercise.getExam().getId() == id).
                    map(exercise -> new ExerciseDTO(exercise)).collect(Collectors.toList());
        }
    }

    public void saveExercises(List<ReceivedExercise> receivedExerciseList) {
        for (ReceivedExercise exercise : receivedExerciseList) {
            String type = getExerciseType(exercise.getId());
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

    public void createExercise(OpenExercise exerciseDTO, Long examId) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(exerciseDTO);

        Exercise exercise = Exercise.builder()
                .exam(examService.getExamById(examId))
                .content(content)
                .build();

        Exercise persisted = exerciseRepository.save(exercise);

        log.info("Exercise of type: {} with ID -> {} <- has been ADDED", exerciseDTO.getType(), persisted.getId());
    }

    public void deleteExercise(Long exerciseId) {
        Exercise exercise = getExerciseById(exerciseId);

        Exam exam = exercise.getExam();
        exam.removeExercise(exercise);

        exerciseRepository.delete(exercise);
        examService.saveExam(exam);
        log.info("Exercise with ID -> {} <- has been DELETED", exercise.getId());
    }
}
