package com.myexaminer.controller;

import com.myexaminer.entity.Lecturer;
import com.myexaminer.service.LecturerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/lecturers")
public class LecturerController {

    private final LecturerService lecturerService;

    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @PostMapping
    public void addLecturer(@RequestBody Lecturer lecturer) {
        lecturerService.createLecturer(lecturer);
    }
}
