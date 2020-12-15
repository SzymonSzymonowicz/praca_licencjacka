package com.myexaminer.service;

import com.myexaminer.model.Exam;
import com.myexaminer.modelDTO.ExamDTO;
import com.myexaminer.repository.ExamRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;

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

        return examById.get();
    }

    public Iterable<Exam> returnAllExams(){
        return examRepository.findAll();
    }
}
