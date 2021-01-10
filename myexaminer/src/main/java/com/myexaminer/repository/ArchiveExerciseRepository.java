package com.myexaminer.repository;

import com.myexaminer.model.ArchiveExercise;
import com.myexaminer.model.Exercise;
import com.myexaminer.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArchiveExerciseRepository extends JpaRepository<ArchiveExercise, Integer> {
    long deleteByIdArchiveExercise(int idArchiveExercise);
    Optional<ArchiveExercise> findByIdArchiveExercise(int idArchiveExercise);
    Optional<ArchiveExercise> findByExerciseIdExerciseAndStudentIdStudent(int idExercise, int idStudent);
}
