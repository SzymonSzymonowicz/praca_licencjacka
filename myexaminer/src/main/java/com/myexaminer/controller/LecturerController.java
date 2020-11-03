package com.myexaminer.controller;

import com.myexaminer.model.Lecturer;
import com.myexaminer.service.LecturerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@Controller
@RequestMapping(path="/lecturer")
public class LecturerController {
    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(AccountController.class);

    private final LecturerService lecturerService;

    public LecturerController(LecturerService lecturerService) { this.lecturerService = lecturerService; }

    @PostMapping(path="/add")
    public @ResponseBody void addNewLecturer (@RequestBody Lecturer lecturer) {
        lecturerService.lecturerSave(lecturer);
        log.info("Lecturer with account_idaccount -> {} <- has been ADDED", lecturer.getIdLecturer());
    }
}
