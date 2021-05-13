package com.myexaminer.component;

import com.myexaminer.entity.Exam;
import com.myexaminer.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Log4j2
@RequiredArgsConstructor
@ConditionalOnProperty(value = "schedule.exam-status-updater.enabled", havingValue = "true")
public class ExamScheduler {

    private final ExamRepository examRepository;

    @Scheduled(cron = "${schedule.exam-status-updater.cron}")
    public void closeExams() {
        List<Exam> exams = examRepository.findAll().stream().filter(exam -> exam.isOpen()).collect(Collectors.toList());

        exams.forEach(this::checkDateAndCloseExam);
    }

    @Scheduled(cron = "${schedule.exam-status-updater.cron}")
    public void openExams() {
        List<Exam> exams = examRepository.findAll().stream().filter(exam -> exam.isHidden()).collect(Collectors.toList());

        exams.forEach(this::checkDateAndOpenExam);
    }

    private void checkDateAndCloseExam(Exam exam) {
        int dateValue = getExamEnd(exam).compareTo(LocalDateTime.now());
        if (dateValue <= 0) {
            exam.setStateToClosed();
            examRepository.save(exam);
            log.info("Exam with ID -> " + exam.getId() + " <- has been CLOSED");
        }
    }

    private void checkDateAndOpenExam(Exam exam) {
        int dateValue1 = exam.getAvailableFrom().compareTo(LocalDateTime.now());
        int dateValue2 = getExamEnd(exam).compareTo(LocalDateTime.now());
        if (dateValue1 <= 0 && dateValue2 > 0) {
            exam.setStateToOpen();
            examRepository.save(exam);
            log.info("Exam with ID -> " + exam.getId() + " <- has been OPENED");
        }
    }

    private LocalDateTime getExamEnd(Exam exam) {
        return exam.getAvailableFrom().plusMinutes(exam.getDuration());
    }
}
