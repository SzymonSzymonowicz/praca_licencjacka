package com.myexaminer.repository;

import com.myexaminer.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    long deleteByIdStudent(int idStudent);
    Optional<Student> findByIdStudent(int idStudent);
    Optional<Student> findByIndex(String index);
}
