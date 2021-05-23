package com.myexaminer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myexaminer.dto.ArchiveExerciseDTO;
import com.myexaminer.entity.ArchiveExercise;
import com.myexaminer.entity.IndividualExam;
import com.myexaminer.enums.RoleEnum;
import com.myexaminer.exerciseTypes.ReceivedExercise;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckingExercisesService {

    private final IndividualExamService individualExamService;
    private final ExerciseService exerciseService;
    private final AccountService accountService;
    private final ArchiveExerciseService archiveExerciseService;

    public void checkExercises(List<ReceivedExercise> receivedExerciseList, Long individualExamId, Long examId, Authentication authentication) throws JsonProcessingException {
        for (ReceivedExercise receivedExercise : receivedExerciseList) {
            Long id = receivedExercise.getId();
            ArchiveExercise archiveExercise = checkIfStudentOrLectorAndReturnArchiveExercise(examId, id, individualExamId, authentication);
            String type = exerciseService.getExerciseType(id);
            archiveExercise.setLecturerComment(receivedExercise.getLecturerComment());
            Object receivedAnswer = receivedExercise.getAnswer();
            switch (type) {
                case "L":
                    archiveExercise.setGainedPoints(receivedExercise.getGainedPoints());
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonArray = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(receivedAnswer);
                    archiveExercise.setAnswer("{\"answerJSON\":" + jsonArray + "}");
                    break;
                case "Z": {
                    if (receivedAnswer == null) {
                        archiveExercise.setGainedPoints(0);
                        archiveExercise.setAnswer(null);
                    } else {
                        String answer = (String) receivedAnswer;
                        String userAnswer = answer.split(",")[1];
                        JSONObject obj = new JSONObject(exerciseService.returnExerciseById(id).getContent());
                        int maxPointsFromExercise = obj.getInt("points");
                        archiveExercise.setGainedPoints((userAnswer.equals("T") ? maxPointsFromExercise : 0));
                        archiveExercise.setAnswer(archiveExerciseService.toJSONString((String) receivedExercise.getAnswer()));
                    }
                    break;
                }
                case "O": {
                    archiveExercise.setGainedPoints(receivedExercise.getGainedPoints());
                    archiveExercise.setAnswer(archiveExerciseService.toJSONString((String) receivedAnswer));
                    break;
                }
            }

            archiveExercise.setLecturerComment(receivedExercise.getLecturerComment());
            archiveExerciseService.exerciseSave(archiveExercise);

            if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(RoleEnum.ROLE_LECTURER.name()))) {
                individualExamService.setIndividualExamToChecked(id);
            }
        }
    }

    public ArchiveExercise checkIfStudentOrLectorAndReturnArchiveExercise(Long examId, Long exerciseId, Long individualExamId, Authentication authentication) {
        ArchiveExercise archiveExercise;
        if (examId == null) {
            archiveExercise = archiveExerciseService
                    .getArchiveExerciseByExerciseAndIndividualExam(
                            exerciseId,
                            individualExamId
                    );
        } else {
            Long idStudent = accountService.getAccountByEmail(authentication.getName()).getId();
            Long individualExamId1 = individualExamService.returnIndividualExamByIdStudentAndIdExam(idStudent, examId).getId();
            archiveExercise = archiveExerciseService
                    .getArchiveExerciseByExerciseAndIndividualExam(
                            exerciseId,
                            individualExamId1
                    );
        }

        return archiveExercise;
    }

    public List<ArchiveExerciseDTO> returnCheckedExercises(Long examId, Long individualExamId, Authentication authentication) {

        List<ArchiveExerciseDTO> archiveExerciseDTOS;
        if (individualExamId == null) {
            Long idStudent = accountService.getAccountByEmail(authentication.getName()).getId();
            Long dbIndividualExam = individualExamService.returnIndividualExamByIdStudentAndIdExam(idStudent, examId).getId();
            archiveExerciseDTOS = archiveExerciseService.archiveExercisesDTOByExamIdAndIndividualExamId(examId, dbIndividualExam);
        } else {
            IndividualExam individualExam = individualExamService.getIndividualExamById(individualExamId);
            archiveExerciseDTOS = archiveExerciseService.archiveExercisesDTOByExamIdAndIndividualExamId(individualExam.getMainExam().getId(), individualExamId);
        }

        return archiveExerciseDTOS;
    }
}