package com.myexaminer.controller;

import com.myexaminer.exerciseTypes.ReceivedExercise;
import com.myexaminer.exerciseTypes.ReceivedExercisesWithStudentEmail;
import com.myexaminer.model.ArchiveExercise;
import com.myexaminer.model.Exercise;
import com.myexaminer.modelDTO.ArchiveExerciseDTO;
import com.myexaminer.modelDTO.TwoIdObject;
import com.myexaminer.service.*;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Controller
@RequestMapping(path="/archive")
public class ArchiveExerciseController {

    private final ExerciseService exerciseService;
    private final ArchiveExerciseService archiveExerciseService;
    private final ExamService examService;
    private final StudentService studentService;
    private final AccountService accountService;

    public ArchiveExerciseController(ExerciseService exerciseService, ArchiveExerciseService archiveExerciseService,
                                     ExamService examService, StudentService studentService, AccountService accountService){
        this.exerciseService = exerciseService;
        this.archiveExerciseService = archiveExerciseService;
        this.examService = examService;
        this.studentService = studentService;
        this.accountService = accountService;
    }

    @PostMapping("/createExerciseArchive")
    public ResponseEntity<HttpStatus> createExerciseArchive(@RequestBody TwoIdObject twoIdObject) {
        int idStudent = twoIdObject.getIdStudent();
        int idExam = twoIdObject.getIdExam();
        if(archiveExerciseService.returnArchiveExerciseByExerciseAndStudent(examService.returnExamById(idExam).getExercises().get(0), studentService.returnStudentById(idStudent)).isPresent()){
            return ResponseEntity.ok(HttpStatus.CONFLICT);
        } else {
            for (Exercise exercise : examService.returnExamById(idExam).getExercises()) {
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
    }

    @PutMapping("/checkExercises")
    public ResponseEntity<HttpStatus> checkExercises(@RequestBody ReceivedExercisesWithStudentEmail receivedExerciseWithStudentEmail){
        for(ReceivedExercise receivedExercise: receivedExerciseWithStudentEmail.getReceivedExercises()){
            int idExercise = receivedExercise.getIdExercise();
            String type = exerciseService.getExerciseType(idExercise);
            ArchiveExercise archiveExercise = archiveExerciseService
                    .returnArchiveExerciseByExerciseAndStudent(
                            exerciseService.returnExerciseById(idExercise),
                            studentService.returnStudentById(accountService.returnAccountByEmail(receivedExerciseWithStudentEmail.getStudentEmail()).get().getIdAccount())
                    ).get();

            archiveExercise.setLecturerComment(receivedExercise.getComment());
            Object receivedAnswer = receivedExercise.getAnswer();
            switch (type) {
                case "L":
                    archiveExercise.setGainedPoints(receivedExercise.getPoints());
                    archiveExercise.setAnswer(archiveExerciseService.toJSONArray((List<String>) receivedAnswer));
                    break;
                case "Z": {
                    if(receivedAnswer == null) {
                        archiveExercise.setGainedPoints(0);
                        archiveExercise.setAnswer(null);
                    }
                    else {
                        String answer = (String) receivedAnswer;
                        String userAnswer = answer.split(",")[1];
                        JSONObject obj = new JSONObject(exerciseService.returnExerciseById(idExercise).getExerciseBody());
                        int maxPointsFromExercise = obj.getInt("points");
                        archiveExercise.setGainedPoints((userAnswer.equals("T") ? maxPointsFromExercise : 0));
                        archiveExercise.setAnswer(archiveExerciseService.toJSONString((String) receivedExercise.getAnswer()));
                    }
                    break;
                }
                case "O": {
                    archiveExercise.setGainedPoints(receivedExercise.getPoints());
                    archiveExercise.setAnswer(archiveExerciseService.toJSONString((String) receivedAnswer));
                    break;
                }
            }
            archiveExerciseService.exerciseSave(archiveExercise);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/getExercises")
    public @ResponseBody Iterable<ArchiveExerciseDTO> getExercises(@RequestParam int idExam, @RequestParam int idStudent){
        return archiveExerciseService.archiveExercisesDTOByExamIdAndIdStudent(idExam, idStudent);
    }
}
