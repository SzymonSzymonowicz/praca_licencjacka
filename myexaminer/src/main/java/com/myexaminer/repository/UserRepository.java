package com.myexaminer.repository;

import com.myexaminer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    long deleteByIdUser(int idUser);
    Optional<User> findByIdUser(int idUser);
}
