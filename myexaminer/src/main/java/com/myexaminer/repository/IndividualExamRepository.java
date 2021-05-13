package com.myexaminer.repository;

import com.myexaminer.entity.IndividualExam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndividualExamRepository extends JpaRepository<IndividualExam, Long> {
    Optional<IndividualExam> findByStudentIdAndMainExamId(Long studentId, Long examId);
    //findByStudentIdStudentAndMainExamIdExam
}
