package com.myexaminer.controller;

import com.myexaminer.model.Notebook;
import com.myexaminer.modelDTO.GenericOneValue;
import com.myexaminer.service.NotebookService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Controller
@RequestMapping(path="/notebook")
public class NotebookController {

    private final NotebookService notebookService;

    public NotebookController(NotebookService notebookService){
        this.notebookService = notebookService;
    }

    @GetMapping
    @ResponseBody
    public String getNotebookContent(HttpServletRequest request){
        return notebookService.returnNotebookByUserEmail(request.getUserPrincipal().getName()).getNotebookBody();
    }

    @PutMapping
    public ResponseEntity<HttpStatus> editNotebookContent(HttpServletRequest request, @RequestBody GenericOneValue content){
        String user = request.getUserPrincipal().getName();
        String notebookContent;
        if(content != null){
            notebookContent = (String) content.getFirstValue();}
        else {
            notebookContent = "";
        }

        Notebook notebook = notebookService.returnNotebookByUserEmail(user);

        notebook.setNotebookBody(notebookContent);

        notebookService.notebookSave(notebook);

        log.info("Notebook with ID -> {} <- has been UPDATED by user -> {} <- with value: -> {} <-", notebook.getIdNotebook(), user, notebookContent);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
