package com.myexaminer.repository;

import com.myexaminer.model.IndividualExam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndividualExamRepository extends JpaRepository<IndividualExam, Integer> {
    long deleteByIdIndividualExam(int idIndividualExam);
    Optional<IndividualExam> findByStudentIdStudentAndMainExamIdExam(int idStudent, int idExam);
    IndividualExam findByIdIndividualExam(int idIndividualExam);
}
