package com.myexaminer.repository;

import com.myexaminer.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Integer> {
    long deleteByIdExam(int idExam);
    Optional<Exam> findByIdExam(int idExam);
}
