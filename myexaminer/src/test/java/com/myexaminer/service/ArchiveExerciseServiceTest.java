package com.myexaminer.service;

import com.myexaminer.model.ArchiveExercise;
import com.myexaminer.model.Exam;
import com.myexaminer.model.Exercise;
import com.myexaminer.model.IndividualExam;
import com.myexaminer.modelDTO.ArchiveExerciseDTO;
import com.myexaminer.repository.ArchiveExerciseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ArchiveExerciseServiceTest {

    @Mock
    private ArchiveExerciseRepository archiveExerciseRepository;

    @Mock
    private ExamService examService;

    @Mock
    private IndividualExamService individualExamService;

    @InjectMocks
    private ArchiveExerciseService archiveExerciseService;

    @Test
    void When_NoArchiveExerciseByExerciseIdAndIndividualExamIf_Throw_NoSuchElementException(){
        //given
        when(archiveExerciseRepository.findByExerciseIdExerciseAndIndividualExamIdIndividualExam(1,1)).thenReturn(Optional.empty());
        when(archiveExerciseService.returnOptionalArchiveExerciseByExerciseAndIndividualExam(1,1)).thenReturn(Optional.empty());

        //when //then
        assertThrows(NoSuchElementException.class, () -> {
            archiveExerciseService.returnArchiveExerciseByExerciseAndIndividualExam(1,1);
        });
    }

    @Test
    void When_ArchiveExerciseDoesNotExist_Expect_False(){
        //given
        when(archiveExerciseService.returnOptionalArchiveExerciseByExerciseAndIndividualExam(1,1)).thenReturn(Optional.empty());

        //when
        boolean result = archiveExerciseService.doArchiveExerciseExists(1,1);

        //then
        assertThat(result).isFalse();
    }

    @Test
    void When_ArchiveExerciseExists_Expect_True(){
        //given
        ArchiveExercise archiveExercise = ArchiveExercise.builder()
                .id(1)
                .build();

        when(archiveExerciseService.returnOptionalArchiveExerciseByExerciseAndIndividualExam(1,1)).thenReturn(Optional.of(archiveExercise));

        //when
        boolean result = archiveExerciseService.doArchiveExerciseExists(1,1);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void When_CreateNewArchiveExercises_Expect_Save(){
        //given
        Exercise exercise1 = Exercise.builder()
                .idExercise(1)
                .build();
        Exercise exercise2 = Exercise.builder()
                .idExercise(2)
                .build();
        List<Exercise> exerciseList = Arrays.asList(exercise1, exercise2);

        IndividualExam individualExam = IndividualExam.builder()
                .idIndividualExam(1)
                .build();

        ArgumentCaptor<ArchiveExercise> argumentCaptor = ArgumentCaptor.forClass(ArchiveExercise.class);

        //when
        archiveExerciseService.createNewArchiveExercises(exerciseList, individualExam);

        //then
        verify(archiveExerciseRepository, times(2)).save(argumentCaptor.capture());

        assertThat(1).isEqualTo(argumentCaptor.getAllValues().get(0).getExercise().getIdExercise());
        assertThat(2).isEqualTo(argumentCaptor.getAllValues().get(1).getExercise().getIdExercise());
    }

    @Test
    void When_ArchiveExercisesDTOByExamIdAndIdIndividualExam_Expect_ListOfArchiveExerciseDTO(){
        //given
        Exercise exercise1 = Exercise.builder()
                .idExercise(1)
                .build();
        Exercise exercise2 = Exercise.builder()
                .idExercise(2)
                .build();

        List<Exercise> exerciseList = Arrays.asList(exercise1, exercise2);

        Exam exam = Exam.builder()
                .idExam(1)
                .exercises(exerciseList)
                .build();

        IndividualExam individualExam = IndividualExam.builder()
                .idIndividualExam(1)
                .build();

        ArchiveExercise archiveExercise1 = ArchiveExercise.builder()
                .id(1)
                .gainedPoints(3)
                .answer("answer1")
                .lecturerComment("comment1")
                .exercise(exercise1)
                .individualExam(individualExam)
                .build();

        ArchiveExercise archiveExercise2 = ArchiveExercise.builder()
                .id(2)
                .gainedPoints(2)
                .answer("answer2")
                .lecturerComment("comment2")
                .exercise(exercise2)
                .individualExam(individualExam)
                .build();

        when(examService.returnExamById(1)).thenReturn(exam);
        doReturn(Optional.of(archiveExercise1)).when(archiveExerciseRepository).findByExerciseIdExerciseAndIndividualExamIdIndividualExam(1, 1);
        doReturn(Optional.of(archiveExercise2)).when(archiveExerciseRepository).findByExerciseIdExerciseAndIndividualExamIdIndividualExam(2, 1);

        //when
        List<ArchiveExerciseDTO> archiveExerciseDTOList = archiveExerciseService.archiveExercisesDTOByExamIdAndIdIndividualExam(1, 1);

        //then
        assertThat(archiveExerciseDTOList.get(0).getIdArchiveExercise()).isEqualTo(1);
        assertThat(archiveExerciseDTOList.get(1).getIdArchiveExercise()).isEqualTo(2);

        assertThat(archiveExerciseDTOList.get(0).getIdExercise()).isEqualTo(1);
        assertThat(archiveExerciseDTOList.get(1).getIdExercise()).isEqualTo(2);

        assertThat(archiveExerciseDTOList.get(0).getAnswer()).isEqualTo("answer1");
        assertThat(archiveExerciseDTOList.get(1).getAnswer()).isEqualTo("answer2");

        assertThat(archiveExerciseDTOList.get(0).getLecturerComment()).isEqualTo("comment1");
        assertThat(archiveExerciseDTOList.get(1).getLecturerComment()).isEqualTo("comment2");

        assertThat(archiveExerciseDTOList.get(0).getIdIndividualExam()).isEqualTo(1);
        assertThat(archiveExerciseDTOList.get(1).getIdIndividualExam()).isEqualTo(1);

        assertThat(archiveExerciseDTOList.get(0).getGainedPoints()).isEqualTo(3);
        assertThat(archiveExerciseDTOList.get(1).getGainedPoints()).isEqualTo(2);
    }
}
