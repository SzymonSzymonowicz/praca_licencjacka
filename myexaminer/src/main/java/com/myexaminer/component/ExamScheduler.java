package com.myexaminer.component;

import com.myexaminer.model.Exam;
import com.myexaminer.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Log4j2
@RequiredArgsConstructor
public class ExamScheduler {

    private final ExamRepository examRepository;

    @Scheduled(cron = "0 * * * * *")
    public void closeExams(){
        List<Exam> exams = examRepository.findAll().stream().filter(exam -> !exam.isClosed()).collect(Collectors.toList());

        exams.forEach(this::checkDateAndCloseExam);
    }

    @Scheduled(cron = "0 * * * * *")
    public void openExams(){
        List<Exam> exams = examRepository.findAll().stream().filter(exam -> !exam.isOpened()).collect(Collectors.toList());

        exams.forEach(this::checkDateAndOpenExam);
    }

    private void checkDateAndCloseExam(Exam exam){
        int dateValue = exam.getExamAvailableDate().plusMinutes(exam.getExamDurationTime()).compareTo(LocalDateTime.now());
        if(dateValue <= 0){
            exam.setStatusToClosed();
            examRepository.save(exam);
            log.info("Exam with ID -> " + exam.getIdExam() + " <- has been CLOSED");
        }
    }

    private void checkDateAndOpenExam(Exam exam){
        int dateValue1 = exam.getExamAvailableDate().compareTo(LocalDateTime.now());
        int dateValue2 = exam.getExamAvailableDate().plusMinutes(exam.getExamDurationTime()).compareTo(LocalDateTime.now());
        if(dateValue1 <= 0 && dateValue2 > 0){
            exam.setStatusToOpen();
            examRepository.save(exam);
            log.info("Exam with ID -> " + exam.getIdExam() + " <- has been OPENED");
        }
    }
}
