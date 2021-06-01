package com.myexaminer.service;

import com.myexaminer.entity.*;
import com.myexaminer.enums.RoleEnum;
import com.myexaminer.repository.TeachingGroupRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.*;

import static com.myexaminer.component.DateUtils.parseStringToDate;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TeachingGroupServiceTest {

    @Mock
    private TeachingGroupRepository teachingGroupRepository;

    @Mock
    private StudentService studentService;

    @Mock
    private LecturerService lecturerService;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TeachingGroupService teachingGroupService;

    @Test
    void When_NoTeachingGroupByIdInRepository_Expect_EntityNotFoundException(){
        //given
        when(teachingGroupRepository.findById(1L)).thenReturn(Optional.empty());

        //when //then
        assertThrows(EntityNotFoundException.class, () -> {
            teachingGroupService.getTeachingGroupById(1L);
        });
    }

    @Test
    void When_TeachingGroupExistsByName_Expect_True(){
        //given
        TeachingGroup teachingGroup = TeachingGroup.builder().id(1L).name("group").build();

        when(teachingGroupRepository.findByName("group")).thenReturn(Optional.of(teachingGroup));

        //when
        boolean bool = teachingGroupService.teachingGroupExistsByName("group");

        //then
        assertThat(bool).isTrue();
    }

    @Test
    void When_StudentIdAndTeachingGroupIdGiven_Expect_StudentAddedToGroup(){
        //given
        Set<TeachingGroup> teachingGroupSet = new HashSet<>();
        Set<Student> studentSet = new HashSet<>();
        TeachingGroup teachingGroup = TeachingGroup.builder().id(1L).students(studentSet).build();
        Student student = Student.builder().id(1L).firstName("Jasiu").teachingGroups(teachingGroupSet).build();

        when(teachingGroupRepository.findById(1L)).thenReturn(Optional.of(teachingGroup));
        when(studentService.getStudentById(1L)).thenReturn(student);

        ArgumentCaptor<TeachingGroup> argumentCaptor = ArgumentCaptor.forClass(TeachingGroup.class);

        //when
        teachingGroupService.addStudentToTeachingGroup(1L, 1L);

        //then
        verify(teachingGroupRepository, times(1)).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getAllValues().get(0).getStudents().size()).isEqualTo(1);
        assertThat(argumentCaptor.getAllValues().get(0).getStudents().stream().findFirst().get().getFirstName()).isEqualTo("Jasiu");
    }

    @Test
    void When_TeachingGroupWithSameNameAlreadyExists_Expect_EntityExistsException(){
        //given
        TeachingGroup teachingGroup = TeachingGroup.builder().id(1L).name("group").build();
        when(teachingGroupRepository.findByName("group")).thenReturn(Optional.of(teachingGroup));
        Authentication authentication = Mockito.mock(Authentication.class);

        //when //then
        assertThrows(EntityExistsException.class, () -> {
            teachingGroupService.createTeachingGroup("group", authentication);
        });
    }

    @Test
    void When_NoTeachingGroupWithSameNameAlreadyExists_Expect_SaveNewTeachingGroup(){
        //given
        Authentication authentication = Mockito.mock(Authentication.class);
        Account account = Account.builder().id(1L).email("email").build();
        Lecturer lecturer = Lecturer.builder().id(1L).account(account).build();

        when(teachingGroupRepository.findByName("group")).thenReturn(Optional.empty());
        when(authentication.getName()).thenReturn("email");
        when(lecturerService.findLecturerByEmail("email")).thenReturn(lecturer);

        ArgumentCaptor<TeachingGroup> argumentCaptor = ArgumentCaptor.forClass(TeachingGroup.class);

        //when
        teachingGroupService.createTeachingGroup("group", authentication);

        //then
        verify(teachingGroupRepository, times(1)).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getAllValues().get(0).getName()).isEqualTo("group");
        assertThat(argumentCaptor.getAllValues().get(0).getAccessCode().length()).isEqualTo(8);
        assertThat(argumentCaptor.getAllValues().get(0).getLecturer()).isEqualTo(lecturer);
    }

    @Test
    void When_NoTeachingGroupByCodeInRepository_Expect_EntityNotFoundException(){
        //given
        when(teachingGroupRepository.findByAccessCode("12345678")).thenReturn(Optional.empty());

        //when //then
        assertThrows(EntityNotFoundException.class, () -> {
            teachingGroupService.findTeachingGroupByAccessCode("12345678");
        });
    }

    @Test
    void When_StudentGivesCorrectCode_Expect_StudentAddedToGroup(){
        //given
        Set<Student> studentSet = new HashSet<>();
        Authentication authentication = Mockito.mock(Authentication.class);
        Student student = Student.builder().id(1L).firstName("Jasiu").build();
        TeachingGroup teachingGroup = TeachingGroup.builder().id(1L).accessCode("12345678").students(studentSet).build();

        when(teachingGroupRepository.findByAccessCode("12345678")).thenReturn(Optional.of(teachingGroup));
        when(authentication.getName()).thenReturn("email");
        when(studentService.findStudentByEmail("email")).thenReturn(student);

        ArgumentCaptor<TeachingGroup> argumentCaptor = ArgumentCaptor.forClass(TeachingGroup.class);

        //when
        teachingGroupService.addStudentToGroupByCode("12345678", authentication);

        //then
        verify(teachingGroupRepository, times(1)).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getAllValues().get(0).getStudents().size()).isEqualTo(1);
        assertThat(argumentCaptor.getAllValues().get(0).getStudents().stream().findFirst().get().getFirstName()).isEqualTo("Jasiu");
    }

    @Test
    void When_UserWithRoleSTUDENTWantsToGetTeachingGroups_Expect_ListOfTeachinogGroups(){
        //given
        Role role = Role.builder().id(1L).name(RoleEnum.ROLE_STUDENT).build();
        Account account = Account.builder().id(1L).roles(Set.of(role)).build();
        Student student = Student.builder().id(1L).account(account).build();
        TeachingGroup teachingGroup1 = TeachingGroup.builder().id(1L).students(Set.of(student)).build();
        TeachingGroup teachingGroup2 = TeachingGroup.builder().id(2L).students(Set.of(student)).build();

        when(accountService.getAccountById(1L)).thenReturn(account);
        when(studentService.getStudentByAccountId(1L)).thenReturn(student);
        when(teachingGroupRepository.findAllByStudents(student)).thenReturn(List.of(teachingGroup1, teachingGroup2));

        //when
        List<TeachingGroup> teachingGroups = teachingGroupService.getTeachingGroupByAccountId(1L);

        //then
        assertThat(teachingGroups.size()).isEqualTo(2);
        assertThat(teachingGroups.get(0).getId()).isEqualTo(1L);
        assertThat(teachingGroups.get(1).getId()).isEqualTo(2L);
    }

    @Test
    void When_UserEditTeachingGroup_Expect_EditedTeachingGroup(){
        //given
        TeachingGroup teachingGroupBody = TeachingGroup.builder().name("groupEdited").startingDate(parseStringToDate("2021-05-09 00:05:00")).build();
        TeachingGroup teachingGroupOriginal = TeachingGroup.builder().id(1L).name("groupOriginal").startingDate(parseStringToDate("2021-05-09 00:05:15")).build();

        when(teachingGroupRepository.findById(1L)).thenReturn(Optional.of(teachingGroupOriginal));

        ArgumentCaptor<TeachingGroup> argumentCaptor = ArgumentCaptor.forClass(TeachingGroup.class);

        //when
        teachingGroupService.editGroup(1L, teachingGroupBody);

        //then
        verify(teachingGroupRepository, times(1)).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getAllValues().get(0).getName()).isEqualTo("groupEdited");
        assertThat(argumentCaptor.getAllValues().get(0).getStartingDate()).isEqualTo(parseStringToDate("2021-05-09 00:05:00"));
    }
}
