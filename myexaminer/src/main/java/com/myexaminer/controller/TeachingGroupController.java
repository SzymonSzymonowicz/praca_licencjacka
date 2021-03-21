package com.myexaminer.controller;

import com.myexaminer.model.TeachingGroup;
import com.myexaminer.service.TeachingGroupService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(path = "/groups")
public class TeachingGroupController {
    private final TeachingGroupService teachingGroupService;

    public TeachingGroupController(TeachingGroupService teachingGroupService) {
        this.teachingGroupService = teachingGroupService;
    }

    @PostMapping
    public void addTeachingGroup(@RequestBody TeachingGroup teachingGroup) {
        teachingGroupService.createTeachingGroup(teachingGroup);
    }

    @PostMapping(path = "/student")
    public void addStudentToGroup(@RequestParam int idTeachingGroup, @RequestParam int idStudent) {
        teachingGroupService.addStudentToGroup(idTeachingGroup, idStudent);
    }
}
