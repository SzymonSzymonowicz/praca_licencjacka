package com.myexaminer.model;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "exam")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id")
    private int idExam;

    @Column(name = "exam_name")
    private String examName;

    @Column(name = "exam_description")
    private String examDescription;

    @Column(name = "exam_available_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date examAvailableDate;

    @Column(name = "exam_duration_time")
    private Integer examDurationTime;

    @ManyToOne
    @JoinColumn(name="fk_teaching_group_id", nullable=false)
    private TeachingGroup teachingGroup;

    @OneToMany(mappedBy="exam")
    private List<Exercise> exercises;

    public int getIdExam() {
        return idExam;
    }

    public void setIdExam(int idExam) {
        this.idExam = idExam;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public TeachingGroup getTeachingGroup() {
        return teachingGroup;
    }

    public void setTeachingGroup(TeachingGroup teachingGroup) {
        this.teachingGroup = teachingGroup;
    }

    public String getExamDescription() {
        return examDescription;
    }

    public void setExamDescription(String examDescription) {
        this.examDescription = examDescription;
    }

    public Date getExamAvailableDate() {
        return examAvailableDate;
    }

    public void setExamAvailableDate(Date examAvailableDate) {
        this.examAvailableDate = examAvailableDate;
    }

    public Integer getExamDurationTime() {
        return examDurationTime;
    }

    public void setExamDurationTime(Integer examDurationTime) {
        this.examDurationTime = examDurationTime;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Exam(String examName, String examDescription, Date examAvailableDate, Integer examDurationTime, TeachingGroup teachingGroup, List<Exercise> exercises) {
        this.examName = examName;
        this.examDescription = examDescription;
        this.examAvailableDate = examAvailableDate;
        this.examDurationTime = examDurationTime;
        this.teachingGroup = teachingGroup;
        this.exercises = exercises;
    }

    public Exam() {
    }

    @Override
    public String toString() {
        return "Exam{" +
                "idExam=" + idExam +
                ", examName='" + examName + '\'' +
                ", examDescription='" + examDescription + '\'' +
                ", examAvailableDate=" + examAvailableDate +
                ", examDurationTime=" + examDurationTime +
                ", teachingGroup=" + teachingGroup.getIdTeachingGroup() +
                '}';
    }
}
