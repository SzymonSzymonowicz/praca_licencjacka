package com.myexaminer.controller;

import com.myexaminer.entity.Lesson;
import com.myexaminer.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/lessons/{id}")
    public Lesson getLessonById(@PathVariable Long id) {
        return lessonService.getLessonById(id);
    }

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @PostMapping("/groups/{groupId}/lessons")
    public void createLessonForGivenGroup(@PathVariable Long groupId, @RequestBody Lesson lesson) {
        lessonService.createLessonForGivenGroup(groupId, lesson);
    }

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @DeleteMapping("/groups/{groupId}/lessons/{lessonId}")
    public void deleteLesson(@PathVariable Long groupId, @PathVariable Long lessonId) {
        lessonService.deleteLesson(groupId, lessonId);
    }

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @PatchMapping("/lessons/{lessonId}")
    public void updateLesson(@PathVariable Long lessonId, @RequestBody Lesson lesson) {
        lessonService.updateLesson(lessonId, lesson);
    }
}
