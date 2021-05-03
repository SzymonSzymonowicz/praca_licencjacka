package com.myexaminer.controller;

import com.myexaminer.model.TeachingGroup;
import com.myexaminer.modelDTO.AccessCodeDTO;
import com.myexaminer.modelDTO.TeachingGroupDTO;
import com.myexaminer.service.TeachingGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping(path = "/groups")
@RequiredArgsConstructor
public class TeachingGroupController {

    private final TeachingGroupService teachingGroupService;

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
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @PostMapping
    public void addTeachingGroup(@RequestBody TeachingGroupDTO teachingGroupDTO, Authentication authentication) {
        teachingGroupService.createTeachingGroup(teachingGroupDTO, authentication);
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

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping(path = "/students")
    public ResponseEntity addStudentToGroupByCode(@RequestBody AccessCodeDTO accessCodeDTO, Authentication authentication){
        return teachingGroupService.addStudentToGroupByCode(accessCodeDTO, authentication);
    }

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @GetMapping("/lecturers")
    public List<TeachingGroup> getLectorsTeachingGroups(Authentication authentication){
        return teachingGroupService.returnTeachingGroupsByLecturerEmail(authentication.getName());
    }

    @GetMapping
    public List<TeachingGroup> getTeachingGroups(){
        return teachingGroupService.findAllGroups();
    }
}
