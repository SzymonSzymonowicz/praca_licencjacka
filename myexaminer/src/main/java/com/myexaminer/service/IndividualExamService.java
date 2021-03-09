package com.myexaminer.service;

import com.myexaminer.model.Exam;
import com.myexaminer.model.IndividualExam;
import com.myexaminer.model.Student;
import com.myexaminer.repository.IndividualExamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class IndividualExamService {

    private final IndividualExamRepository individualExamRepository;
    private final StudentService studentService;
    private final ExamService examService;

    public IndividualExamService(IndividualExamRepository individualExamRepository, StudentService studentService, ExamService examService){
        this.individualExamRepository = individualExamRepository;
        this.studentService = studentService;
        this.examService = examService;
    }

    public void individualExamSave(IndividualExam individualExam) {
        individualExamRepository.save(individualExam);
    }

    public IndividualExam returnIndividualExamById(int idIndividualExam){
        return individualExamRepository.findByIdIndividualExam(idIndividualExam);
    }

    public Optional<IndividualExam> returnOptionalIndividualExamByIdStudentAndIdExam(int idStudent, int idExam){
        return individualExamRepository.findByStudentIdStudentAndMainExamIdExam(idStudent, idExam);
    }

    public IndividualExam returnIndividualExamByIdStudentAndIdExam(int idStudent, int idExam){
        return returnOptionalIndividualExamByIdStudentAndIdExam(idStudent, idExam)
                .orElseThrow(() -> new NoSuchElementException("There is no Individual Exam in database that you were looking for."));
    }

    public IndividualExam createOrGetIndividualExamAndReturn(int idStudent, int idExam){
        Student student = studentService.returnStudentById(idStudent);
        Exam exam = examService.returnExamById(idExam);
        Optional<IndividualExam> individualExamOpt = returnOptionalIndividualExamByIdStudentAndIdExam(idStudent, idExam);
        IndividualExam individualExam;
        if(individualExamOpt.isEmpty()){
            individualExam = new IndividualExam(exam, student);
            individualExamSave(individualExam);
        }
        else {
            individualExam = individualExamOpt.get();
        }

        return individualExam;
    }

    @Transactional
    public void setIndividualExamToChecked(int idIndividualExam){
        IndividualExam individualExam = returnIndividualExamById(idIndividualExam);
        individualExam.setChecked(true);
        individualExamSave(individualExam);
    }

}
