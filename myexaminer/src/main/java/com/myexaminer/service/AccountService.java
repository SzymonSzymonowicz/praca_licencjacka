package com.myexaminer.service;

import com.myexaminer.model.Account;

import com.myexaminer.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired(required = true)
    private AccountRepository accountRepository;

/*    public AccountService(AccountRepository accountRepository){this.accountRepository = accountRepository;}*/

    public void accountSave(Account account) { accountRepository.save(account); }
}
