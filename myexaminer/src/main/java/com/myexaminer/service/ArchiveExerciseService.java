package com.myexaminer.service;

import com.myexaminer.dto.ArchiveExerciseDTO;
import com.myexaminer.dto.StudentExam;
import com.myexaminer.entity.ArchiveExercise;
import com.myexaminer.entity.Exercise;
import com.myexaminer.entity.IndividualExam;
import com.myexaminer.repository.ArchiveExerciseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ArchiveExerciseService {

    private final ArchiveExerciseRepository archiveExerciseRepository;
    private final ExamService examService;
    private final IndividualExamService individualExamService;

    public void exerciseSave(ArchiveExercise archiveExercise) {
        archiveExerciseRepository.save(archiveExercise);
    }

    public Optional<ArchiveExercise> getOptionalArchiveExerciseByExerciseAndIndividualExam(Long exerciseId, Long individualExamId) {
        return archiveExerciseRepository.findByExerciseIdAndIndividualExamId(exerciseId, individualExamId);
    }

    public ArchiveExercise getArchiveExerciseByExerciseAndIndividualExam(Long exerciseId, Long individualExamId) {

        return getOptionalArchiveExerciseByExerciseAndIndividualExam(exerciseId, individualExamId)
                .orElseThrow(() -> new EntityNotFoundException("There is no Archive Exercise in database that you were looking for."));
    }

    public boolean doArchiveExerciseExists(Long exerciseId, Long individualExamId) {
        return getOptionalArchiveExerciseByExerciseAndIndividualExam(exerciseId, individualExamId).isPresent();
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

    public List<ArchiveExerciseDTO> archiveExercisesDTOByExamIdAndIndividualExamId(Long examId, Long individualExamId) {
        List<ArchiveExercise> archiveExerciseList = new ArrayList<>();
        for (Exercise exercise : examService.getExamById(examId).getExercises()) {
            archiveExerciseList.add(
                    getArchiveExerciseByExerciseAndIndividualExam(exercise.getId(), individualExamId)
            );
        }

        return archiveExerciseList.stream()
                .map(exercise -> new ArchiveExerciseDTO((exercise)))
                .collect(Collectors.toList());
    }

    public String toJSONString(String answer) {
        StringBuilder jsonAnswer = new StringBuilder("{\"answerJSON\":\"");
        jsonAnswer.append(answer);
        jsonAnswer.append("\"}");

        return jsonAnswer.toString();
    }

    public void createExerciseArchive(StudentExam studentExam) {
        Long studentId = studentExam.getStudentId();
        Long examId = studentExam.getExamId();

        IndividualExam individualExam = individualExamService
                .createOrGetIndividualExamAndReturn(studentId, examId);

        if (doArchiveExerciseExists(
                examService.getExamById(examId).getExercises().get(0).getId(),
                individualExam.getId())) {
            return;
        } else {
            createNewArchiveExercises(examService.getExamById(examId).getExercises(), individualExam);
            return;
        }
    }
}