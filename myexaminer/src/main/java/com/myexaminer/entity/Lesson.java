package com.myexaminer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    private String topic;

    private String description;

    @ManyToOne
    @JsonIgnore
    private TeachingGroup teachingGroup;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<Chapter> chapters;

    public void addChapter(Chapter chapter) {
        chapters.add(chapter);
        chapter.setLesson(this);
    }

    public void removeChapter(Chapter chapter) {
        chapters.remove(chapter);
    }
}
