package com.myexaminer.service;

import com.myexaminer.model.Notebook;
import com.myexaminer.modelDTO.GenericOneValue;
import com.myexaminer.repository.NotebookRepository;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Service
public class NotebookService {

    private final NotebookRepository notebookRepository;

    public NotebookService(NotebookRepository notebookRepository) {
        this.notebookRepository = notebookRepository;
    }

    public void notebookSave(Notebook notebook) {
        notebookRepository.save(notebook);
    }

    public Notebook returnNotebookByUserEmail(String email) {
        return notebookRepository.findByAccountEmail(email);
    }


    public void updateNotebookContentForLoggedInUser(HttpServletRequest request, GenericOneValue content) {
        String user = request.getUserPrincipal().getName();
        String notebookContent;

        if (content != null) {
            notebookContent = (String) content.getFirstValue();
        } else {
            notebookContent = "";
        }

        Notebook notebook = returnNotebookByUserEmail(user);

        notebook.setNotebookBody(notebookContent);

        notebookSave(notebook);

        log.info("Notebook with ID -> {} <- has been UPDATED by user -> {} <- with value: -> {} <-", notebook.getIdNotebook(), user, notebookContent);
    }
}