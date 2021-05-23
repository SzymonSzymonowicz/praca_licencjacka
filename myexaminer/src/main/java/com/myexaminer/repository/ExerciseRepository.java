package com.myexaminer.repository;

import com.myexaminer.entity.Exam;
import com.myexaminer.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByExam(Exam exam);
}
