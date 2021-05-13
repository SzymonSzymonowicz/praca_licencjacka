package com.myexaminer.repository;

import com.myexaminer.entity.ArchiveExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArchiveExerciseRepository extends JpaRepository<ArchiveExercise, Long> {
    Optional<ArchiveExercise> findByExerciseIdAndIndividualExamId(Long exerciseId, Long individualExamId);
}
