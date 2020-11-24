package com.myexaminer.controller;

import com.myexaminer.model.TeachingGroup;
import com.myexaminer.service.TeachingGroupService;
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
@RequestMapping(path="/teachinggroup")
public class TeachingGroupController {
    private final TeachingGroupService teachingGroupService;
    private final LecturerService lecturerService;

    public TeachingGroupController(TeachingGroupService teachingGroupService, LecturerService lecturerService) {
        this.teachingGroupService = teachingGroupService;
        this.lecturerService = lecturerService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addNewTeachingGroup (@RequestBody TeachingGroup teachingGroup) {
        if(teachingGroupService.teachingGroupExistsById(teachingGroup.getIdTeachingGroup())){
            log.info("Group with given ID -> {} <- ALREADY EXISTS", teachingGroup.getIdTeachingGroup());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        if(!lecturerService.lecturerExistsById(teachingGroup.getIdLecturer())){
            log.info("Lecturer with given ID -> {} <- DOES NOT EXIST", teachingGroup.getIdLecturer());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        if(teachingGroupService.teachingGroupExistsByName(teachingGroup.getTeachingGroupName())){
            log.info("Group with given name -> {} <- ALREADY EXISTS", teachingGroup.getTeachingGroupName());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        teachingGroupService.teachingGroupSave(teachingGroup);
        log.info("Group with ID -> {} <- has been ADDED", teachingGroup.getIdTeachingGroup());

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
