package com.myexaminer.repository;

import com.myexaminer.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    long deleteByidAccount(int idAccount);
    Account findByidAccount(int idAccount);
    Optional<Account> findByEmail(String email);
}
