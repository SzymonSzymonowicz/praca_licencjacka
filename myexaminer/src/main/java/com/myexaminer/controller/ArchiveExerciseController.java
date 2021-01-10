package com.myexaminer.controller;

import com.google.gson.Gson;
import com.myexaminer.exerciseTypes.ReceivedExercise;
import com.myexaminer.exerciseTypes.ReceivedExerciseWithStudentId;
import com.myexaminer.model.ArchiveExercise;
import com.myexaminer.model.Exercise;
import com.myexaminer.model.Student;
import com.myexaminer.modelDTO.TwoIdObject;
import com.myexaminer.service.*;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Log4j2
@Controller
@RequestMapping(path="/archive")
public class ArchiveExerciseController {

    private final ExerciseService exerciseService;
    private final ArchiveExerciseService archiveExerciseService;
    private final ExamService examService;
    private final StudentService studentService;

    public ArchiveExerciseController(ExerciseService exerciseService, ArchiveExerciseService archiveExerciseService,
                                     ExamService examService, StudentService studentService){
        this.exerciseService = exerciseService;
        this.archiveExerciseService = archiveExerciseService;
        this.examService = examService;
        this.studentService = studentService;
    }

    @PostMapping("/createExerciseArchive")
    public ResponseEntity<HttpStatus> createExerciseArchive(@RequestBody TwoIdObject twoIdObject) {
        int idStudent = twoIdObject.getIdStudent();
        int idExam = twoIdObject.getIdExam();
        for(Exercise exercise: examService.returnExamById(idExam).getExercises()){
            archiveExerciseService.exerciseSave(new ArchiveExercise(
                    exercise,
                    studentService.returnStudentById(idStudent),
                    0,
                    null,
                    null
            ));
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/checkExercises")
    public ResponseEntity<HttpStatus> checkExercises(@RequestBody ReceivedExerciseWithStudentId receivedExerciseWithStudentId){
        for(ReceivedExercise receivedExercise: receivedExerciseWithStudentId.getReceivedExercises()){
            int taskId = receivedExercise.getTaskId();
            String type = exerciseService.getExerciseType(taskId);
            ArchiveExercise archiveExercise = archiveExerciseService.returnArchiveExerciseByExerciseAndStudent(exerciseService.returnExerciseById(taskId), studentService.returnStudentById(receivedExerciseWithStudentId.getIdStudent()));
            archiveExercise.setLecturerComment(receivedExercise.getComment());
            switch (type) {
                case "L":
                    archiveExercise.setGainedPoints(receivedExercise.getPoints());
                    archiveExercise.setAnswer(archiveExerciseService.toJSONArray((List<String>) receivedExercise.getAnswer()));
                    break;
                case "Z": {
                    String answer = (String) receivedExercise.getAnswer();
                    String userAnswer = answer.split(", ")[1];
                    JSONObject obj = new JSONObject(exerciseService.returnExerciseById(taskId).getExerciseBody());
                    int maxPointsFromExercise = obj.getInt("points");
                    archiveExercise.setGainedPoints((userAnswer.equals("T") ? maxPointsFromExercise : 0));
                    archiveExercise.setAnswer(archiveExerciseService.toJSONString((String) receivedExercise.getAnswer()));
                    break;
                }
                case "O": {
                    archiveExercise.setGainedPoints(receivedExercise.getPoints());
                    archiveExercise.setAnswer(archiveExerciseService.toJSONString((String) receivedExercise.getAnswer()));
                    break;
                }
            }
            archiveExerciseService.exerciseSave(archiveExercise);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
