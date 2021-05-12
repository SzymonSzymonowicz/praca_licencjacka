package com.myexaminer.repository;

import com.myexaminer.model.Exam;
import com.myexaminer.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByExam(Exam exam);
}
