package com.myexaminer.service;

import com.myexaminer.model.IndividualExam;
import com.myexaminer.repository.IndividualExamRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IndividualExamService {

    private final IndividualExamRepository individualExamRepository;

    public IndividualExamService(IndividualExamRepository individualExamRepository){
        this.individualExamRepository = individualExamRepository;
    }

    public void individualExamSave(IndividualExam individualExam) {
        individualExamRepository.save(individualExam);
    }

    public IndividualExam returnIndividualExamById(int idIndividualExam){
        return individualExamRepository.findByIdIndividualExam(idIndividualExam);
    }

    public Optional<IndividualExam> returnIndividualExamByIdStudentAndIdExam(int idStudent, int idExam){
        return individualExamRepository.findByStudentIdStudentAndMainExamIdExam(idStudent, idExam);
    }

}
