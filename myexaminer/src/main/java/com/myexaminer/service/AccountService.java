package com.myexaminer.service;

import com.myexaminer.model.Account;

import com.myexaminer.repository.AccountRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){this.accountRepository = accountRepository;}

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void accountSave(Account account) {
        accountRepository.save(account);
    }

    public boolean accountExistsById(int idAccount){
        Optional<Account> accountById = accountRepository.findByidAccount(idAccount);

        return accountById.isPresent();
    }

    public boolean accountExistsByEmail(String email){
        Optional<Account> accountByEmail = accountRepository.findByEmail(email);

        if(accountByEmail.isPresent()){
            log.info("Account with given email -> {} <- ALREADY EXISTS", email);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkCredentials(Account account){
        Optional<Account> accountFromDB = accountRepository.findByEmail(account.getEmail());

        if (accountFromDB.isEmpty()
                || !passwordEncoder.matches(account.getPassword(), accountFromDB.get().getPassword())) {
            return false;
        }

        return true;
    }

    public Account returnAccountByEmail(String email){
        Optional<Account> accountByEmail = accountRepository.findByEmail(email);

        return accountByEmail.orElseThrow(() -> new UsernameNotFoundException("Could not find account with given email"));
    }
}
