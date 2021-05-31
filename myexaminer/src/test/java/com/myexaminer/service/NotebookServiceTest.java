package com.myexaminer.service;

import com.myexaminer.dto.GenericOneValue;
import com.myexaminer.entity.Notebook;
import com.myexaminer.repository.NotebookRepository;
import com.myexaminer.security.service.AccountDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class NotebookServiceTest {

    @Mock
    private NotebookRepository notebookRepository;

    @InjectMocks
    private NotebookService notebookService;

    @Test
    void When_LoggedUserWriteNoteInNotebook_Expect_NotebookUpdate(){
        //given
        Authentication authentication = Mockito.mock(Authentication.class);
        AccountDetails details = Mockito.mock(AccountDetails.class);

        GenericOneValue genericOneValue = new GenericOneValue("Note");

        Notebook notebook = Notebook.builder().id(1L).content("").build();

        when(authentication.getPrincipal()).thenReturn(details);
        when(details.getEmail()).thenReturn("email");
        when(notebookRepository.findByAccountEmail("email")).thenReturn(notebook);

        ArgumentCaptor<Notebook> argumentCaptor = ArgumentCaptor.forClass(Notebook.class);

        //when
        notebookService.updateNotebookContentForLoggedInUser(authentication, genericOneValue);

        //then
        verify(notebookRepository, times(1)).save(argumentCaptor.capture());

        assertThat(argumentCaptor.getAllValues().get(0).getContent()).isEqualTo("Note");
    }
}
