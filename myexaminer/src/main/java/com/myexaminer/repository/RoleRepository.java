package com.myexaminer.repository;

import com.myexaminer.enums.RoleEnum;
import com.myexaminer.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
    Optional<Role> findByName(RoleEnum roleEnum);
}