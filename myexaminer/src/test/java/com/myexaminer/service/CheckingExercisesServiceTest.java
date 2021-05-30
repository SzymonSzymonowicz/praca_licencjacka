package com.myexaminer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myexaminer.entity.ArchiveExercise;
import com.myexaminer.entity.Exam;
import com.myexaminer.entity.Exercise;
import com.myexaminer.entity.IndividualExam;
import com.myexaminer.exerciseTypes.ReceivedExercise;
import com.myexaminer.repository.ArchiveExerciseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class CheckingExercisesServiceTest {

    @Mock
    private IndividualExamService individualExamService;

    @Mock
    private ExerciseService exerciseService;

    @Mock
    private AccountService accountService;

    @Mock
    private ArchiveExerciseService archiveExerciseService;

    @Mock
    private ArchiveExerciseRepository archiveExerciseRepository;

    @InjectMocks
    private CheckingExercisesService checkingExercisesService;

    @Test
    void When_CheckExercise_Expect_SaveArchiveExercise() throws JsonProcessingException {
        //given
        ReceivedExercise receivedExercise1 = ReceivedExercise.builder()
                .id(1L)
                .answer("a hot potato,T")
                .lecturerComment("comment1")
                .gainedPoints(1)
                .build();

        ReceivedExercise receivedExercise2 = ReceivedExercise.builder()
                .id(2L)
                .answer("answer2")
                .lecturerComment("comment2")
                .gainedPoints(2)
                .build();

        List<ReceivedExercise> receivedExerciseList = List.of(receivedExercise1, receivedExercise2);

        String content1 = "{\"type\": \"Z\", \"instruction\": \"The subject of bullying and fighting in my school is:\", \"answers\": [\"apple of eye,F\", \"piece of cake,F\", \"a bed of roses,F\", \"a hot potato,T\"], \"points\":1}";
        String content2 = "{\"type\": \"O\", \"instruction\": \"Zastosuj inwersję: It was only after Sir Fredrick and Charles discovered insulin that diabets patient got the chance to live\", \"points\":2}";

        Exercise exercise1_Z = Exercise.builder()
                .id(1L)
                .content(content1)
                .build();

        Exercise exercise2_O = Exercise.builder()
                .id(2L)
                .content(content2)
                .build();

        Exam exam = Exam.builder()
                .id(1L)
                .exercises(List.of(exercise1_Z, exercise2_O)).build();

        ArchiveExercise archiveExercise1 = ArchiveExercise.builder().id(1L).exercise(exercise1_Z).build();
        ArchiveExercise archiveExercise2 = ArchiveExercise.builder().id(2L).exercise(exercise2_O).build();

        IndividualExam individualExam = IndividualExam.builder()
                .id(1L)
                .archiveExercises(List.of(archiveExercise1, archiveExercise2))
                .mainExam(exam).build();

        exam.setIndividualExams(List.of(individualExam));

        exercise1_Z.setArchiveExercises(List.of(archiveExercise1));
        exercise2_O.setArchiveExercises(List.of(archiveExercise2));

        Authentication authentication = Mockito.mock(Authentication.class);

        when(archiveExerciseService.getArchiveExerciseByExerciseAndIndividualExam(1L,1L)).thenReturn(archiveExercise1);
        when(archiveExerciseService.getArchiveExerciseByExerciseAndIndividualExam(2L,1L)).thenReturn(archiveExercise2);
        when(exerciseService.getExerciseType(1L)).thenReturn("Z");
        when(exerciseService.getExerciseType(2L)).thenReturn("O");
        when(exerciseService.returnExerciseById(1L)).thenReturn(exercise1_Z);

        //when
        checkingExercisesService.checkExercises(receivedExerciseList, 1L, null, authentication);

        //then
        assertThat(archiveExercise2.getGainedPoints()).isEqualTo(2);
        assertThat(archiveExercise1.getGainedPoints()).isEqualTo(1);
        assertThat(archiveExercise2.getAnswer()).isEqualTo("answer2");
        assertThat(archiveExercise1.getAnswer()).isEqualTo("a hot potato,T");
        assertThat(archiveExercise2.getLecturerComment()).isEqualTo("comment2");
        assertThat(archiveExercise1.getLecturerComment()).isEqualTo("comment1");
    }
}
