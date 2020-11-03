package com.myexaminer.repository;

import com.myexaminer.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    long deleteByidAccount(int idAccount);
    Account findByidAccount(int idAccount);
}
