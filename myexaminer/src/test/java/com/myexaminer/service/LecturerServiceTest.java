package com.myexaminer.service;

import com.myexaminer.entity.Account;
import com.myexaminer.entity.Lecturer;
import com.myexaminer.repository.LecturerRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class LecturerServiceTest {

    @Mock
    private LecturerRepository lecturerRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private LecturerService lecturerService;

    @Test
    void When_AccountWithGivenIdExistsAndNoLecturerWithGivenId_Expect_CreateNewLecturer(){
        //given
        Account account = Account.builder().id(1L).build();
        Lecturer lecturer = Lecturer.builder()
                .id(1L)
                .account(account)
                .build();

        when(accountService.accountExistsById(lecturer.getId())).thenReturn(true);
        when(lecturerRepository.findById(1L)).thenReturn(Optional.empty());

        ArgumentCaptor<Lecturer> argumentCaptor = ArgumentCaptor.forClass(Lecturer.class);

        //when
        lecturerService.createLecturer(lecturer);

        //then
        verify(lecturerRepository, times(1)).save(argumentCaptor.capture());

        assertThat(argumentCaptor.getAllValues().get(0).getAccount()).isEqualTo(account);
        assertThat(argumentCaptor.getAllValues().get(0).getId()).isEqualTo(1L);
    }

    @Test
    void When_NoLecturerByEmail_Expect_EntityNotFoundException(){
        //given
        when(lecturerRepository.findByAccount_Email("email")).thenReturn(Optional.empty());

        //when //then
        assertThrows(EntityNotFoundException.class, () -> {
            lecturerService.findLecturerByEmail("email");
        });
    }

    @Test
    void When_NoLecturerByAccountId_Expect_EntityNotFoundException(){
        //given
        when(lecturerRepository.findByAccountId(1L)).thenReturn(Optional.empty());

        //when //then
        assertThrows(EntityNotFoundException.class, () -> {
            lecturerService.getLecturerByAccountId(1L);
        });
    }
}
