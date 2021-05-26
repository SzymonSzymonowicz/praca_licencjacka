package com.myexaminer.service;

import com.myexaminer.entity.Lesson;
import com.myexaminer.entity.TeachingGroup;
import com.myexaminer.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;

    private final TeachingGroupService teachingGroupService;

    public Lesson getLessonById(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Lesson of id: " + lessonId + " was not found."));
    }

    public void createLessonForGivenGroup(Long groupId, Lesson lesson) {
        TeachingGroup group = teachingGroupService.getTeachingGroupById(groupId);
        lesson.setTeachingGroup(group);

        Lesson persistedLesson = lessonRepository.save(lesson);

        group.addLesson(persistedLesson);
        teachingGroupService.saveTeachingGroup(group);
    }

    public void deleteLesson(Long groupId, Long lessonId) {
        TeachingGroup group = teachingGroupService.getTeachingGroupById(groupId);
        Lesson lesson = getLessonById(lessonId);

        group.removeLesson(lesson);
        teachingGroupService.saveTeachingGroup(group);

        lessonRepository.delete(lesson);
    }

    public void updateLesson(Long lessonId, Lesson lesson) {
        Lesson dbLesson = getLessonById(lessonId);

        if (StringUtils.isNotBlank(lesson.getTopic())) {
            dbLesson.setTopic(lesson.getTopic());
        }

        if (StringUtils.isNotBlank(lesson.getDescription())) {
            dbLesson.setDescription(lesson.getDescription());
        }

        if (!Objects.isNull(lesson.getDate())) {
            dbLesson.setDate(lesson.getDate());
        }

        lessonRepository.save(dbLesson);
    }
}
