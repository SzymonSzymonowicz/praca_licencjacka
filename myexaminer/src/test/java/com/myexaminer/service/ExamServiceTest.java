package com.myexaminer.service;

import com.myexaminer.dto.ExamDTO;
import com.myexaminer.dto.GenericOneValue;
import com.myexaminer.dto.GenericTwoValues;
import com.myexaminer.entity.Exam;
import com.myexaminer.entity.TeachingGroup;
import com.myexaminer.enums.State;
import com.myexaminer.repository.ExamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.myexaminer.component.DateUtils.parseStringToDate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ExamServiceTest {

    @Mock
    private ExamRepository examRepository;

    @Mock
    private TeachingGroupService teachingGroupService;

    @InjectMocks
    private ExamService examService;

    @Test
    void When_ExamExists_Expect_True(){
        //given
        Exam exam = Exam.builder().id(1L).build();

        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));

        //when
        boolean bool = examService.examExistsById(1L);

        //then
        assertThat(bool).isTrue();
    }

    @Test
    void When_NoExamInRepository_Expect_EntityNotFoundException(){
        //given
        when(examRepository.findById(1L)).thenReturn(Optional.empty());

        //when //then
        assertThrows(EntityNotFoundException.class, () -> {
            examService.getExamById(1L);
        });
    }

    @Test
    void When_ExamInRepository_Expect_ReturnedExam(){
        //given
        Exam exam = Exam.builder().id(1L).build();

        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));

        Map<String, Long> map = new HashMap<>();
        map.put("id", 1L);

        //when
        Exam returnedExam = examService.getExam(map);

        //then
        assertThat(returnedExam).isNotNull();
    }

    @Test
    void When_CreateExam_Expect_NewExam(){
        //given
        TeachingGroup teachingGroup = TeachingGroup.builder()
                .id(1L).build();

        ExamDTO examDTO = ExamDTO.builder()
                .name("exam1")
                .description("description1")
                .availableFrom("2021-05-09 00:05:00")
                .duration(60L).build();

        ArgumentCaptor<Exam> argumentCaptor = ArgumentCaptor.forClass(Exam.class);

        when(teachingGroupService.getTeachingGroupById(1L)).thenReturn(teachingGroup);

        //when
        examService.createExam(examDTO, 1L);

        //then
        verify(examRepository, times(1)).save(argumentCaptor.capture());

        assertThat(argumentCaptor.getAllValues().get(0).getName()).isEqualTo("exam1");
        assertThat(argumentCaptor.getAllValues().get(0).getDescription()).isEqualTo("description1");
        assertThat(argumentCaptor.getAllValues().get(0).getState()).isEqualTo(State.DRAFT);
        assertThat(argumentCaptor.getAllValues().get(0).getDuration()).isEqualTo(60L);
        assertThat(argumentCaptor.getAllValues().get(0).getAvailableFrom()).isEqualTo(parseStringToDate("2021-05-09 00:05:00"));
        assertThat(argumentCaptor.getAllValues().get(0).getTeachingGroup()).isEqualTo(teachingGroup);
    }

    @Test
    void When_GetExamDTOSByIdGroup_Expect_ListOfExamDTOS(){
        //given
        TeachingGroup teachingGroup1 = TeachingGroup.builder()
                .id(1L).build();

        TeachingGroup teachingGroup2 = TeachingGroup.builder()
                .id(2L).build();

        Exam exam1 = Exam.builder()
                .id(1L)
                .name("Exam1")
                .description("desc1")
                .availableFrom(parseStringToDate("2021-05-09 00:05:00"))
                .duration(60L)
                .state(State.HIDDEN)
                .teachingGroup(teachingGroup1)
                .build();

        Exam exam2 = Exam.builder()
                .id(2L)
                .name("Exam2")
                .description("desc2")
                .availableFrom(parseStringToDate("2021-05-09 00:05:00"))
                .duration(60L)
                .state(State.CLOSED)
                .teachingGroup(teachingGroup2)
                .build();

        when(examRepository.findAll()).thenReturn(List.of(exam1, exam2));

        //when
        List<ExamDTO> examDTOS = examService.getExamDTOSByIdGroup(1L);

        //then
        assertThat(examDTOS.size()).isEqualTo(1);
        assertThat(examDTOS.get(0).getName()).isEqualTo("Exam1");
        assertThat(examDTOS.get(0).getDescription()).isEqualTo("desc1");
        assertThat(examDTOS.get(0).getDuration()).isEqualTo(60L);
        assertThat(examDTOS.get(0).getAvailableFrom()).isEqualTo("2021-05-09T00:05");
        assertThat(examDTOS.get(0).getState()).isEqualTo(State.HIDDEN.toString());
    }

    @Test
    void When_GetState_Expect_State(){
        //given
        GenericOneValue genericOneValue = new GenericOneValue(1L);

        Exam exam = Exam.builder().id(1L).state(State.CLOSED).build();

        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));

        //when
        State state = examService.getState(genericOneValue);

        //then
        assertThat(state).isEqualTo(State.CLOSED);
    }

    @Test
    void When_UpdateExamStatus_Expect_StatusUpdated(){
        //given
        GenericTwoValues genericTwoValues = new GenericTwoValues(1L, "OPEN");

        Exam exam = Exam.builder().id(1L).state(State.CLOSED).build();

        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));

        ArgumentCaptor<Exam> argumentCaptor = ArgumentCaptor.forClass(Exam.class);

        //when
        examService.updateExamStatus(genericTwoValues);

        //then
        verify(examRepository, times(1)).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getAllValues().get(0).getState()).isEqualTo(State.OPEN);
    }
}
