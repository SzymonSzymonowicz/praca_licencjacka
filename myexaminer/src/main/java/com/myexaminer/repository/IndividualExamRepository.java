package com.myexaminer.repository;

import com.myexaminer.entity.IndividualExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndividualExamRepository extends JpaRepository<IndividualExam, Long> {
    Optional<IndividualExam> findByStudentIdAndMainExamId(Long studentId, Long examId);
}
