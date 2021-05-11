package com.myexaminer.repository;

import com.myexaminer.model.ArchiveExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArchiveExerciseRepository extends JpaRepository<ArchiveExercise, Long> {
    Optional<ArchiveExercise> findByExerciseIdExerciseAndIndividualExamIdIndividualExam(Long idExercise, Long idIndividualExam);
}
