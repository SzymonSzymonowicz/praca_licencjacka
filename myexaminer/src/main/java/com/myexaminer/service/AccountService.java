package com.myexaminer.service;

import com.myexaminer.model.Account;

import com.myexaminer.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired(required = true)
    private AccountRepository accountRepository;

/*  public AccountService(AccountRepository accountRepository){this.accountRepository = accountRepository;}*/

    public void accountSave(Account account) {
        accountRepository.save(account);
    }

    public boolean accountExistsById(Account account){
        Optional<Account> accountById = accountRepository.findById(account.getIdAccount());

        if(accountById.isPresent())
            return true;

        return false;
    }

    public boolean accountExistsByEmail(Account account){
        Optional<Account> accountByEmail = accountRepository.findByEmail(account.getEmail());

        if(accountByEmail.isPresent())
            return true;

        return false;
    }
}
