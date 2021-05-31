package com.myexaminer.service;

import com.myexaminer.dto.GenericOneValue;
import com.myexaminer.entity.Notebook;
import com.myexaminer.repository.NotebookRepository;
import com.myexaminer.security.service.AccountDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Service
@RequiredArgsConstructor
public class NotebookService {

    private final NotebookRepository notebookRepository;

    public void notebookSave(Notebook notebook) {
        notebookRepository.save(notebook);
    }

    public Notebook returnNotebookByUserEmail(String email) {
        return notebookRepository.findByAccountEmail(email);
    }

    public void updateNotebookContentForLoggedInUser(Authentication authentication, GenericOneValue content) {
        AccountDetails details = (AccountDetails) authentication.getPrincipal();
        String userEmail = details.getEmail();
        String notebookContent;

        if (content != null) {
            notebookContent = (String) content.getFirstValue();
        } else {
            notebookContent = "";
        }

        Notebook notebook = returnNotebookByUserEmail(userEmail);

        notebook.setContent(notebookContent);

        notebookSave(notebook);

        log.info("Notebook with ID -> {} <- has been UPDATED by user -> {} <- with value: -> {} <-", notebook.getId(), userEmail, notebookContent);
    }
}
