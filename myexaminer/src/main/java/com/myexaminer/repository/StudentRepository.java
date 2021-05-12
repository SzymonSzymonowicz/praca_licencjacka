package com.myexaminer.repository;

import com.myexaminer.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByIndex(String index);

    Optional<Student> findByAccountEmail(String email);

    Optional<Student> findByAccountId(Long accountId);
}
