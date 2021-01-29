package com.myexaminer.repository;

import com.myexaminer.model.ArchiveExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArchiveExerciseRepository extends JpaRepository<ArchiveExercise, Integer> {
    long deleteByIdArchiveExercise(int idArchiveExercise);
    Optional<ArchiveExercise> findByIdArchiveExercise(int idArchiveExercise);
    Optional<ArchiveExercise> findByExerciseIdExerciseAndIndividualExamIdIndividualExam(int idExercise, int idIndividualExam);
}
