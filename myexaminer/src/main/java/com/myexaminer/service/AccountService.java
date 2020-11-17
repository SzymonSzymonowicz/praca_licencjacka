package com.myexaminer.service;

import com.myexaminer.model.Account;

import com.myexaminer.repository.AccountRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){this.accountRepository = accountRepository;}

    public void accountSave(Account account) {
        accountRepository.save(account);
    }

    public boolean accountExistsById(int idAccount){
        Optional<Account> accountById = accountRepository.findByidAccount(idAccount);

        return accountById.isPresent();
    }

    public boolean accountExistsByEmail(Account account){
        Optional<Account> accountByEmail = accountRepository.findByEmail(account.getEmail());

        return accountByEmail.isPresent();
    }
}
