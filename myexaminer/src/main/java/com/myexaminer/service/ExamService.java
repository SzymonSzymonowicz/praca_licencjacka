package com.myexaminer.service;

import com.myexaminer.model.Exam;
import com.myexaminer.repository.ExamRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ExamService {

    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository){this.examRepository = examRepository;}

    public void examSave(Exam exam) {
        examRepository.save(exam);
    }

    public boolean examExistsById(int idExam){
        Optional<Exam> examById = examRepository.findByIdExam(idExam);

        return examById.isPresent();
    }

    public Exam returnExamById(int idExam){
        Optional<Exam> examById = examRepository.findByIdExam(idExam);

        return examById.orElseThrow(() -> new NoSuchElementException("There is no Exam in database that you were looking for."));
    }

    public Iterable<Exam> returnAllExams(){
        return examRepository.findAll();
    }
}
