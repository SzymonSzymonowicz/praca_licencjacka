package com.myexaminer.service;

import com.myexaminer.entity.Chapter;
import com.myexaminer.entity.Lesson;
import com.myexaminer.repository.ChapterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ChapterService {
    private final ChapterRepository chapterRepository;
    private final LessonService lessonService;

    public Chapter findChapter(Long chapterId) {
        return chapterRepository.findById(chapterId)
                .orElseThrow(() -> new EntityNotFoundException("Chapter with id: " + chapterId + " not found"));
    }

    public void createChapter(Long lessonId, Chapter chapter) {
        Lesson lesson = lessonService.getLessonById(lessonId);
        lesson.addChapter(chapter);
        Chapter persistedChapter = chapterRepository.save(chapter);

        lessonService.saveLesson(lesson);
    }

    public void editChapter(Long chapterId, Chapter chapter) {
        Chapter dbChapter = findChapter(chapterId);

        dbChapter.setTitle(chapter.getTitle());
        dbChapter.setContent(chapter.getContent());

        chapterRepository.save(dbChapter);
    }
}
