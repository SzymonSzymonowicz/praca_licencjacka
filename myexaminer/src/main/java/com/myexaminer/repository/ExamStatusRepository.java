package com.myexaminer.repository;

import com.myexaminer.model.ExamStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamStatusRepository extends JpaRepository<ExamStatus, Integer> {
    long deleteByIdExamStatus(int idExamStatus);
    Optional<ExamStatus> findByIdExamStatus(int idExamStatus);
}
