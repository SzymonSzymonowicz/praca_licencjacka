package com.myexaminer.repository;

import com.myexaminer.entity.ArchiveExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArchiveExerciseRepository extends JpaRepository<ArchiveExercise, Long> {
    Optional<ArchiveExercise> findByExerciseIdAndIndividualExamId(Long exerciseId, Long individualExamId);
}
