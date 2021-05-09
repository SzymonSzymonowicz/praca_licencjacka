package com.myexaminer.repository;

import com.myexaminer.model.ArchiveExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArchiveExerciseRepository extends JpaRepository<ArchiveExercise, Integer> {
    Optional<ArchiveExercise> findByExerciseIdExerciseAndIndividualExamIdIndividualExam(int idExercise, int idIndividualExam);
}
