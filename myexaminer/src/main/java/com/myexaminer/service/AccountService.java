package com.myexaminer.service;

import com.myexaminer.model.Account;

import com.myexaminer.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){this.accountRepository = accountRepository;}

    public void accountSave(Account account) {
        accountRepository.save(account);
    }

    public boolean accountExistsById(int IdAccount){
        Optional<Account> accountById = accountRepository.findByidAccount(IdAccount);

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
