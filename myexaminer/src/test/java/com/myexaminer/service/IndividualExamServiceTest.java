package com.myexaminer.service;

import com.myexaminer.dto.LecturerIndividualExamView;
import com.myexaminer.entity.Exam;
import com.myexaminer.entity.IndividualExam;
import com.myexaminer.entity.Student;
import com.myexaminer.entity.TeachingGroup;
import com.myexaminer.enums.State;
import com.myexaminer.repository.IndividualExamRepository;
import com.myexaminer.security.service.AccountDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.myexaminer.component.DateUtils.parseStringToDate;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class IndividualExamServiceTest {

    @Mock
    private IndividualExamRepository individualExamRepository;

    @Mock
    private StudentService studentService;

    @Mock
    private ExamService examService;

    @Mock
    private TeachingGroupService teachingGroupService;

    @InjectMocks
    private IndividualExamService individualExamService;

    @Test
    void When_NoIndividualExamByIdInRepository_Expect_EntityNotFoundException(){
        //given
        when(individualExamRepository.findById(1L)).thenReturn(Optional.empty());

        //when //then
        assertThrows(EntityNotFoundException.class, () -> {
            individualExamService.getIndividualExamById(1L);
        });
    }

    @Test
    void When_NoIndividualExamByExamIdAndStudentIdInRepository_Expect_EntityNotFoundException(){
        //given
        when(individualExamRepository.findByStudentIdAndMainExamId(1L, 1L)).thenReturn(Optional.empty());

        //when //then
        assertThrows(EntityNotFoundException.class, () -> {
            individualExamService.returnIndividualExamByIdStudentAndIdExam(1L, 1L);
        });
    }

    @Test
    void When_NoIndividualExamInRepository_Expect_CreateNewOne(){
        //given
        Student student = Student.builder().id(1L).build();
        Exam exam = Exam.builder().id(1L).build();

        when(studentService.getStudentById(1L)).thenReturn(student);
        when(examService.getExamById(1L)).thenReturn(exam);
        when(individualExamRepository.findByStudentIdAndMainExamId(1L, 1L)).thenReturn(Optional.empty());

        ArgumentCaptor<IndividualExam> argumentCaptor = ArgumentCaptor.forClass(IndividualExam.class);

        //when
        individualExamService.createOrGetIndividualExamAndReturn(1L, 1L);

        //then
        verify(individualExamRepository, times(1)).save(argumentCaptor.capture());

        assertThat(argumentCaptor.getAllValues().get(0).getMainExam().getId()).isEqualTo(1L);
        assertThat(argumentCaptor.getAllValues().get(0).getStudent().getId()).isEqualTo(1L);
    }

    @Test
    void When_IndividualExamChecked_Expect_ChangedStatus(){
        //given
        IndividualExam individualExam = IndividualExam.builder().id(1L).isChecked(false).build();

        when(individualExamRepository.findById(1L)).thenReturn(Optional.of(individualExam));

        ArgumentCaptor<IndividualExam> argumentCaptor = ArgumentCaptor.forClass(IndividualExam.class);

        //when
        individualExamService.setIndividualExamToChecked(1L);

        //then
        verify(individualExamRepository, times(1)).save(argumentCaptor.capture());

        assertThat(argumentCaptor.getAllValues().get(0).isChecked()).isTrue();
    }

    @Test
    void When_GetLecturerIndividualExamViews_Expect_IndividualExamView(){
        //given
        Authentication authentication = Mockito.mock(Authentication.class);
        AccountDetails details = Mockito.mock(AccountDetails.class);

        Exam exam = Exam.builder()
                .id(1L)
                .name("name")
                .description("desc")
                .duration(60L)
                .availableFrom(parseStringToDate("2021-05-09 00:05:00"))
                .state(State.CHECKED).build();
        TeachingGroup teachingGroup = TeachingGroup.builder().id(1L).name("nameTL").build();
        Student student = Student.builder().id(1L).index("123456").build();
        IndividualExam individualExam = IndividualExam.builder()
                .mainExam(exam)
                .student(student)
                .id(1L)
                .isChecked(false).build();
        exam.setIndividualExams(List.of(individualExam));
        teachingGroup.setExams(Set.of(exam));
        teachingGroup.setStudents(Set.of(student));

        when(authentication.getPrincipal()).thenReturn(details);
        when(details.getId()).thenReturn(1L);
        when(teachingGroupService.getTeachingGroupsByLecturerId(1L)).thenReturn(List.of(teachingGroup));
        when(individualExamRepository.findByStudentIdAndMainExamId(1L, 1L)).thenReturn(Optional.of(individualExam));

        //when
        List<LecturerIndividualExamView> individualExamViewList = individualExamService.getLecturerIndividualExamViews(authentication);

        //then
        assertThat(individualExamViewList.size()).isEqualTo(1);
        assertThat(individualExamViewList.get(0).getIndividualExamId()).isEqualTo(1L);
        assertThat(individualExamViewList.get(0).getName()).isEqualTo("name");
        assertThat(individualExamViewList.get(0).getDescription()).isEqualTo("desc");
        assertThat(individualExamViewList.get(0).getAvailableFrom()).isEqualTo(parseStringToDate("2021-05-09 00:05:00"));
        assertThat(individualExamViewList.get(0).getTeachingGroupId()).isEqualTo(1L);
        assertThat(individualExamViewList.get(0).getTeachingGroupName()).isEqualTo("nameTL");
        assertThat(individualExamViewList.get(0).getStudentIndex()).isEqualTo("123456");
    }
}
