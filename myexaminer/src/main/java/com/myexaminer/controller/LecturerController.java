package com.myexaminer.controller;

import com.myexaminer.model.Lecturer;
import com.myexaminer.service.LecturerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping(path="/lecturer")
public class LecturerController {
    private final LecturerService lecturerService;

    public LecturerController(LecturerService lecturerService) { this.lecturerService = lecturerService; }

    @PostMapping
    public ResponseEntity<HttpStatus> addNewLecturer (@RequestBody Lecturer lecturer) {
        if(lecturerService.lecturerExistsById(lecturer)){
            log.info("Lecturer with idgiven account -> {} <- ALREADY EXISTS", lecturer.getIdLecturer());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        lecturerService.lecturerSave(lecturer);
        log.info("Lecturer with account_idaccount -> {} <- has been ADDED", lecturer.getIdLecturer());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
