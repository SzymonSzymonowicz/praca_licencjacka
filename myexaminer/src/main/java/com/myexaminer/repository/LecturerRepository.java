package com.myexaminer.repository;

import com.myexaminer.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {
    long deleteByIdLecturer(int idLecturer);
    Optional<Lecturer> findByIdLecturer(int idLecturer);
    Optional<Lecturer> findByAccount_Email(String email);
}
