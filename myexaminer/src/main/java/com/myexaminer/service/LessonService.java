package com.myexaminer.service;

import com.myexaminer.entity.Lesson;
import com.myexaminer.entity.TeachingGroup;
import com.myexaminer.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;

    private final TeachingGroupService teachingGroupService;

    public Lesson getLessonById(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Lesson of id: " +lessonId +" was not found."));
    }

    public void createLessonForGivenGroup(Long groupId, Lesson lesson) {
        TeachingGroup group = teachingGroupService.getTeachingGroupById(groupId);

        lesson.setTeachingGroup(group);
        Lesson persistedLesson = lessonRepository.save(lesson);

        List<Lesson> lessons = group.getLessons();
        lessons.add(persistedLesson);

        group.setLessons(lessons);

        teachingGroupService.saveTeachingGroup(group);
    }
}
