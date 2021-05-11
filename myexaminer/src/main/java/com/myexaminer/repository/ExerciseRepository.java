package com.myexaminer.repository;

import com.myexaminer.model.Exam;
import com.myexaminer.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    long deleteByIdExercise(Long idExercise);
    Optional<Exercise> findByIdExercise(Long idExercise);
    List<Exercise> findByExam(Exam exam);
}
