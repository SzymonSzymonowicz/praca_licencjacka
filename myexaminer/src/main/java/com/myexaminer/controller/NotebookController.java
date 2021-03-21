package com.myexaminer.controller;

import com.myexaminer.modelDTO.GenericOneValue;
import com.myexaminer.service.NotebookService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@RestController
@RequestMapping(path = "/notebook")
public class NotebookController {

    private final NotebookService notebookService;

    public NotebookController(NotebookService notebookService) {
        this.notebookService = notebookService;
    }

    @GetMapping
    public String getNotebookContent(HttpServletRequest request) {
        return notebookService.returnNotebookByUserEmail(request.getUserPrincipal().getName()).getNotebookBody();
    }

    @PutMapping
    public void editNotebookContent(HttpServletRequest request, @RequestBody GenericOneValue content) {
        notebookService.updateNotebookContentForLoggedInUser(request, content);
    }
}
