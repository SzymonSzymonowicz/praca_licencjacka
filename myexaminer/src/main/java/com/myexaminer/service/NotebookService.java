package com.myexaminer.service;

import com.myexaminer.model.Account;
import com.myexaminer.model.Notebook;
import com.myexaminer.repository.NotebookRepository;
import org.springframework.stereotype.Service;

@Service
public class NotebookService {

    private final NotebookRepository notebookRepository;

    public NotebookService(NotebookRepository notebookRepository) {
        this.notebookRepository = notebookRepository;
    }

    public void notebookSave(Notebook notebook){
        notebookRepository.save(notebook);
    }

    public Notebook returnNotebookByUserEmail(String email){
        return notebookRepository.findByAccountEmail(email);
    }
}
