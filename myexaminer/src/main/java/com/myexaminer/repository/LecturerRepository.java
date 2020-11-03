package com.myexaminer.repository;

import com.myexaminer.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    long deleteByIdLecturer(int idLecturer);
    Lecturer findByIdLecturer(int idLecturer);
}
