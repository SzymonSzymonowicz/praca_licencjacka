package com.myexaminer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myexaminer.enums.RoleEnum;
import com.myexaminer.exerciseTypes.ReceivedExercise;
import com.myexaminer.model.ArchiveExercise;
import com.myexaminer.model.IndividualExam;
import com.myexaminer.modelDTO.ArchiveExerciseDTO;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckingExercisesService {

    private final IndividualExamService individualExamService;
    private final ExerciseService exerciseService;
    private final AccountService accountService;
    private final ArchiveExerciseService archiveExerciseService;

    public CheckingExercisesService(IndividualExamService individualExamService, ExerciseService exerciseService,
                                    AccountService accountService, ArchiveExerciseService archiveExerciseService){
        this.individualExamService = individualExamService;
        this.exerciseService = exerciseService;
        this.accountService = accountService;
        this.archiveExerciseService = archiveExerciseService;
    }

    public void checkExercises(List<ReceivedExercise> receivedExerciseList, Integer idIndividualExam, Integer idExam, Authentication authentication) throws JsonProcessingException {
        for (ReceivedExercise receivedExercise : receivedExerciseList) {
            int idExercise = receivedExercise.getIdExercise();
            ArchiveExercise archiveExercise = checkIfStudentOrLectorAndReturnArchiveExercise(idExam, idExercise, idIndividualExam, authentication);
            String type = exerciseService.getExerciseType(idExercise);
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
                        JSONObject obj = new JSONObject(exerciseService.returnExerciseById(idExercise).getExerciseBody());
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
                individualExamService.setIndividualExamToChecked(idIndividualExam);
            }
        }
    }

    public ArchiveExercise checkIfStudentOrLectorAndReturnArchiveExercise(Integer idExam, Integer idExercise, Integer idIndividualExam,  Authentication authentication){
        ArchiveExercise archiveExercise;
        if (idExam == null) {
            archiveExercise = archiveExerciseService
                    .returnArchiveExerciseByExerciseAndIndividualExam(
                            idExercise,
                            idIndividualExam
                    );
        } else {
            int idStudent = accountService.getAccountByEmail(authentication.getName()).getAccountId();
            archiveExercise = archiveExerciseService
                    .returnArchiveExerciseByExerciseAndIndividualExam(
                            idExercise,
                            individualExamService.returnIndividualExamByIdStudentAndIdExam(idStudent, idExam).getIdIndividualExam()
                    );
        }

        return archiveExercise;
    }

    public List<ArchiveExerciseDTO> returnCheckedExercises(Integer idExam, Integer idIndExam, Authentication authentication) {

        List<ArchiveExerciseDTO> archiveExerciseDTOS;
        if (idIndExam == null) {
            int idStudent = accountService.getAccountByEmail(authentication.getName()).getAccountId();
            int idIndividualExam = individualExamService.returnIndividualExamByIdStudentAndIdExam(idStudent, idExam).getIdIndividualExam();
            archiveExerciseDTOS = archiveExerciseService.archiveExercisesDTOByExamIdAndIdIndividualExam(idExam, idIndividualExam);
        } else {
            IndividualExam individualExam = individualExamService.returnIndividualExamById(idIndExam);
            archiveExerciseDTOS = archiveExerciseService.archiveExercisesDTOByExamIdAndIdIndividualExam(individualExam.getMainExam().getIdExam(), idIndExam);
        }

        return archiveExerciseDTOS;
    }
}
