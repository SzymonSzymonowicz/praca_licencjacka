package com.myexaminer.repository;

import com.myexaminer.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    Optional<Lecturer> findByAccount_Email(String email);

    Optional<Lecturer> findByAccountId(Long accountId);
}
