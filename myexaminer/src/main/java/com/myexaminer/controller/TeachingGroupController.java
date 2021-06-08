package com.myexaminer.controller;

import com.myexaminer.dto.GroupNameAndId;
import com.myexaminer.entity.TeachingGroup;
import com.myexaminer.service.TeachingGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(path = "/groups")
@RequiredArgsConstructor
@Validated
public class TeachingGroupController {

    private final TeachingGroupService teachingGroupService;

    @GetMapping("/account/{accountId}")
    public List<TeachingGroup> getAllGroupsOfGivenAccount(@PathVariable Long accountId) {
        return teachingGroupService.getTeachingGroupByAccountId(accountId);
    }

    @GetMapping(value = "/name-id/account/{accountId}")
    public List<GroupNameAndId> getAllGroupsNameAndIdOfGivenAccount(@PathVariable Long accountId) {
        return teachingGroupService.getTeachingGroupNameAndIdByAccountId(accountId);
    }

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @PostMapping
    public void addTeachingGroup(@RequestBody @NotBlank String groupName, Authentication authentication) {
        teachingGroupService.createTeachingGroup(groupName, authentication);
    }

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @GetMapping("/unique")
    public boolean checkIfGroupNameIsUnique(@RequestParam String groupName) {
        return !teachingGroupService.teachingGroupExistsByName(groupName);
    }

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @PatchMapping("/{groupId}")
    public void editGroup(@PathVariable Long groupId, @RequestBody TeachingGroup teachingGroup) {
        teachingGroupService.editGroup(groupId, teachingGroup);
    }

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @DeleteMapping(path = "/{groupId}")
    public void removeGroup(@PathVariable Long groupId) {
        teachingGroupService.deleteGroup(groupId);
    }

    @PostMapping(path = "/{groupId}/students")
    public void addStudentToGroup(@PathVariable Long groupId, @RequestParam Long studentId) {
        teachingGroupService.addStudentToGroup(groupId, studentId);
    }

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @DeleteMapping(path = "/{groupId}/students/{id}")
    public void removeStudentFromGroup(@PathVariable Long groupId, @PathVariable("id") Long studentId) {
        teachingGroupService.removeStudentFromGroup(groupId, studentId);
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping(path = "/students")
    public ResponseEntity addStudentToGroupByCode(@RequestBody String accessCode, Authentication authentication) {
        return teachingGroupService.addStudentToGroupByCode(accessCode, authentication);
    }

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @GetMapping("/lecturers")
    public List<TeachingGroup> getLectorsTeachingGroups(Authentication authentication) {
        return teachingGroupService.returnTeachingGroupsByLecturerEmail(authentication.getName());
    }

    @GetMapping
    public List<TeachingGroup> getTeachingGroups() {
        return teachingGroupService.findAllGroups();
    }

    //TODO security SpEL call to check if caller belongs to requested group
    @GetMapping("/{groupId}")
    public TeachingGroup getTeachingGroupById(@PathVariable Long groupId) {
        return teachingGroupService.getTeachingGroupById(groupId);
    }
}
