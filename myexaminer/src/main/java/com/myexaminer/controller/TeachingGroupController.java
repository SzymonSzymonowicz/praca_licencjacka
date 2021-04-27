package com.myexaminer.controller;

import com.myexaminer.model.TeachingGroup;
import com.myexaminer.service.TeachingGroupService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping(path = "/groups")
public class TeachingGroupController {
    private final TeachingGroupService teachingGroupService;

    public TeachingGroupController(TeachingGroupService teachingGroupService) {
        this.teachingGroupService = teachingGroupService;
    }

//    @GetMapping
//    public TeachingGroup getStudentTeachingGroup(@RequestParam int studentId) {
//        return teachingGroupService.getTeachingGroupByStudentId(studentId);
//    }
//
//    @GetMapping("placeholder")
//    public List<TeachingGroup> getLecturerTeachingGroups(@RequestParam int lecturerId) {
//        return teachingGroupService.getTeachingGroupsByLecturerId(lecturerId);
//    }
//
    @PreAuthorize("hasRole('LECTURER')")
    @PostMapping
    public void addTeachingGroup(@RequestBody TeachingGroup teachingGroup) {
        teachingGroupService.createTeachingGroup(teachingGroup);
    }

    @DeleteMapping(path = "/{group}")
    public void removeGroup(@PathVariable("group") int groupId) {
        teachingGroupService.deleteGroup(groupId);
    }

    @PostMapping(path = "/{group}/students")
    public void addStudentToGroup(@PathVariable("group") int idTeachingGroup, @RequestParam int idStudent) {
        teachingGroupService.addStudentToGroup(idTeachingGroup, idStudent);
    }

    @DeleteMapping(path = "/{group}/students/{id}")
    public void removeStudentFromGroup(@PathVariable("group") int groupId, @PathVariable("id") int studentId) {
        teachingGroupService.removeStudentFromGroup(groupId, studentId);
    }
}
