package com.myexaminer.repository;

import com.myexaminer.model.IndividualExam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndividualExamRepository extends JpaRepository<IndividualExam, Long> {
    long deleteByIdIndividualExam(Long idIndividualExam);
    Optional<IndividualExam> findByStudentIdStudentAndMainExamIdExam(Long idStudent, Long idExam);
    IndividualExam findByIdIndividualExam(Long idIndividualExam);
}
