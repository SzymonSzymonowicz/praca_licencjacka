package com.myexaminer.repository;

import com.myexaminer.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByIndex(String index);

    Optional<Student> findByAccountEmail(String email);

    Optional<Student> findByAccountId(Long accountId);
}
