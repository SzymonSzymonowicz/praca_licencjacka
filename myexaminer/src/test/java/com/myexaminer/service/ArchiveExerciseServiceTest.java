package com.myexaminer.service;

import com.myexaminer.entity.ArchiveExercise;
import com.myexaminer.entity.Exam;
import com.myexaminer.entity.Exercise;
import com.myexaminer.entity.IndividualExam;
import com.myexaminer.dto.ArchiveExerciseDTO;
import com.myexaminer.repository.ArchiveExerciseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
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
    void When_NoArchiveExerciseByExerciseIdAndIndividualExam_Throw_NoSuchElementException(){
        //given
        when(archiveExerciseRepository.findByExerciseIdAndIndividualExamId(1L,1L)).thenReturn(Optional.empty());
        when(archiveExerciseService.getOptionalArchiveExerciseByExerciseAndIndividualExam(1L,1L)).thenReturn(Optional.empty());

        //when //then
        assertThrows(EntityNotFoundException.class, () -> {
            archiveExerciseService.getArchiveExerciseByExerciseAndIndividualExam(1L,1L);
        });
    }

    @Test
    void When_ArchiveExerciseDoesNotExist_Expect_False(){
        //given
        when(archiveExerciseService.getOptionalArchiveExerciseByExerciseAndIndividualExam(1L,1L)).thenReturn(Optional.empty());

        //when
        boolean result = archiveExerciseService.doArchiveExerciseExists(1L,1L);

        //then
        assertThat(result).isFalse();
    }

    @Test
    void When_ArchiveExerciseExists_Expect_True(){
        //given
        ArchiveExercise archiveExercise = ArchiveExercise.builder()
                .id(1L)
                .build();

        when(archiveExerciseService.getOptionalArchiveExerciseByExerciseAndIndividualExam(1L,1L)).thenReturn(Optional.of(archiveExercise));

        //when
        boolean result = archiveExerciseService.doArchiveExerciseExists(1L,1L);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void When_CreateNewArchiveExercises_Expect_Save(){
        //given
        Exercise exercise1 = Exercise.builder()
                .id(1L)
                .build();
        Exercise exercise2 = Exercise.builder()
                .id(2L)
                .build();
        List<Exercise> exerciseList = Arrays.asList(exercise1, exercise2);

        IndividualExam individualExam = IndividualExam.builder()
                .id(1L)
                .build();

        ArgumentCaptor<ArchiveExercise> argumentCaptor = ArgumentCaptor.forClass(ArchiveExercise.class);

        //when
        archiveExerciseService.createNewArchiveExercises(exerciseList, individualExam);

        //then
        verify(archiveExerciseRepository, times(2)).save(argumentCaptor.capture());

        assertThat(1L).isEqualTo(argumentCaptor.getAllValues().get(0).getExercise().getId());
        assertThat(2L).isEqualTo(argumentCaptor.getAllValues().get(1).getExercise().getId());
    }

    @Test
    void When_ArchiveExercisesDTOByExamIdAndIdIndividualExam_Expect_ListOfArchiveExerciseDTO(){
        //given
        Exercise exercise1 = Exercise.builder()
                .id(1L)
                .build();
        Exercise exercise2 = Exercise.builder()
                .id(2L)
                .build();

        List<Exercise> exerciseList = Arrays.asList(exercise1, exercise2);

        Exam exam = Exam.builder()
                .id(1L)
                .exercises(exerciseList)
                .build();

        IndividualExam individualExam = IndividualExam.builder()
                .id(1L)
                .build();

        ArchiveExercise archiveExercise1 = ArchiveExercise.builder()
                .id(1L)
                .gainedPoints(3)
                .answer("answer1")
                .lecturerComment("comment1")
                .exercise(exercise1)
                .individualExam(individualExam)
                .build();

        ArchiveExercise archiveExercise2 = ArchiveExercise.builder()
                .id(2L)
                .gainedPoints(2)
                .answer("answer2")
                .lecturerComment("comment2")
                .exercise(exercise2)
                .individualExam(individualExam)
                .build();

        when(examService.getExamById(1L)).thenReturn(exam);
        doReturn(Optional.of(archiveExercise1)).when(archiveExerciseRepository).findByExerciseIdAndIndividualExamId(1L, 1L);
        doReturn(Optional.of(archiveExercise2)).when(archiveExerciseRepository).findByExerciseIdAndIndividualExamId(2L, 1L);

        //when
        List<ArchiveExerciseDTO> archiveExerciseDTOList = archiveExerciseService.archiveExercisesDTOByExamIdAndIndividualExamId(1L, 1L);

        //then
        assertThat(archiveExerciseDTOList.get(0).getArchivedId()).isEqualTo(1);
        assertThat(archiveExerciseDTOList.get(1).getArchivedId()).isEqualTo(2);

        assertThat(archiveExerciseDTOList.get(0).getExerciseId()).isEqualTo(1);
        assertThat(archiveExerciseDTOList.get(1).getExerciseId()).isEqualTo(2);

        assertThat(archiveExerciseDTOList.get(0).getAnswer()).isEqualTo("answer1");
        assertThat(archiveExerciseDTOList.get(1).getAnswer()).isEqualTo("answer2");

        assertThat(archiveExerciseDTOList.get(0).getLecturerComment()).isEqualTo("comment1");
        assertThat(archiveExerciseDTOList.get(1).getLecturerComment()).isEqualTo("comment2");

        assertThat(archiveExerciseDTOList.get(0).getIndividualExamId()).isEqualTo(1);
        assertThat(archiveExerciseDTOList.get(1).getIndividualExamId()).isEqualTo(1);

        assertThat(archiveExerciseDTOList.get(0).getGainedPoints()).isEqualTo(3);
        assertThat(archiveExerciseDTOList.get(1).getGainedPoints()).isEqualTo(2);
    }
}
