package com.myexaminer.repository;

import com.myexaminer.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    
}
