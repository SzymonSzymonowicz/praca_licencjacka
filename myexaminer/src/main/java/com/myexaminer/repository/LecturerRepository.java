package com.myexaminer.repository;

import com.myexaminer.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    long deleteByIdLecturer(Long idLecturer);
    Optional<Lecturer> findByIdLecturer(Long idLecturer);
    Optional<Lecturer> findByAccount_Email(String email);
    Optional<Lecturer> findByAccountId(Long accountId);
}
