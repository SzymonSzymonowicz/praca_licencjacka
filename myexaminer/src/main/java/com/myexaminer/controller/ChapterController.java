package com.myexaminer.controller;

import com.myexaminer.entity.Chapter;
import com.myexaminer.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;

    @PostMapping("/lessons/{lessonId}/chapters")
    public void createChapter(@PathVariable Long lessonId, @RequestBody Chapter chapter) {
        chapterService.createChapter(lessonId, chapter);
    }

    @PostMapping("/chapters/{chapterId}")
    public void editChapter(@PathVariable Long chapterId, @RequestBody Chapter chapter) {
        chapterService.createChapter(chapterId, chapter);
    }

    @DeleteMapping("/lessons/{lessonId}/chapters/{chapterId}")
    public void deleteChapter(@PathVariable Long lessonId, @PathVariable Long chapterId) {
        chapterService.deleteChapter(lessonId, chapterId);
    }
}
