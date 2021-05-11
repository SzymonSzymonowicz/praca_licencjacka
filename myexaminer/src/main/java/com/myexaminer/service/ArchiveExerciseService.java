package com.myexaminer.service;

import com.myexaminer.model.ArchiveExercise;
import com.myexaminer.model.Exercise;
import com.myexaminer.model.IndividualExam;
import com.myexaminer.modelDTO.ArchiveExerciseDTO;
import com.myexaminer.modelDTO.TwoIdObject;
import com.myexaminer.repository.ArchiveExerciseRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ArchiveExerciseService {

    private final ArchiveExerciseRepository archiveExerciseRepository;
    private final ExamService examService;
    private final IndividualExamService individualExamService;

    public ArchiveExerciseService(ArchiveExerciseRepository archiveExerciseRepository, ExamService examService, IndividualExamService individualExamService) {
        this.archiveExerciseRepository = archiveExerciseRepository;
        this.examService = examService;
        this.individualExamService = individualExamService;
    }

    public void exerciseSave(ArchiveExercise archiveExercise) {
        archiveExerciseRepository.save(archiveExercise);
    }

    public Optional<ArchiveExercise> returnOptionalArchiveExerciseByExerciseAndIndividualExam(Long idExercise, Long idIndividualExam) {

        return archiveExerciseRepository.findByExerciseIdExerciseAndIndividualExamIdIndividualExam(idExercise, idIndividualExam);
    }

    public ArchiveExercise returnArchiveExerciseByExerciseAndIndividualExam(Long idExercise, Long idIndividualExam) {

        return returnOptionalArchiveExerciseByExerciseAndIndividualExam(idExercise, idIndividualExam)
                .orElseThrow(() -> new NoSuchElementException("There is no Archive Exercise in database that you were looking for."));
    }

    public boolean doArchiveExerciseExists(Long idExercise, Long idIndividualExam) {
        return returnOptionalArchiveExerciseByExerciseAndIndividualExam(idExercise, idIndividualExam).isPresent();
    }

    public void createNewArchiveExercises(List<Exercise> exerciseList, IndividualExam individualExam) {
        for (Exercise exercise : exerciseList) {
            ArchiveExercise archiveExercise = new ArchiveExercise(
                    exercise,
                    individualExam,
                    0,
                    null,
                    null
            );

            exerciseSave(archiveExercise);

            log.info("ArchiveExercise with ID -> {} <- has been CREATED and SAVED to database", archiveExercise.getId());
        }
    }

    public List<ArchiveExerciseDTO> archiveExercisesDTOByExamIdAndIdIndividualExam(Long idExam, Long idIndividualExam) {
        List<ArchiveExercise> archiveExerciseList = new ArrayList<>();
        for (Exercise exercise : examService.returnExamById(idExam).getExercises()) {
            archiveExerciseList.add(
                    returnArchiveExerciseByExerciseAndIndividualExam(exercise.getIdExercise(), idIndividualExam)
            );
        }

        return archiveExerciseList.stream().map(exercise -> new ArchiveExerciseDTO((exercise))).collect(Collectors.toList());
    }

    public String toJSONString(String answer) {
        StringBuilder jsonAnswer = new StringBuilder("{\"answerJSON\":\"");
        jsonAnswer.append(answer);
        jsonAnswer.append("\"}");

        return jsonAnswer.toString();
    }

    public void createExerciseArchive(TwoIdObject twoIdObject) {
        Long idStudent = twoIdObject.getIdStudent();
        Long idExam = twoIdObject.getIdExam();

        IndividualExam individualExam = individualExamService
                .createOrGetIndividualExamAndReturn(idStudent, idExam);

        if (doArchiveExerciseExists(
                examService.returnExamById(idExam).getExercises().get(0).getIdExercise(),
                individualExam.getIdIndividualExam())) {
            return;
        } else {
            createNewArchiveExercises(examService.returnExamById(idExam).getExercises(), individualExam);
            return;
        }
    }
}
