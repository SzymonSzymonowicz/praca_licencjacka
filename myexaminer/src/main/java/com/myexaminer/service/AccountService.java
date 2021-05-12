package com.myexaminer.service;

import com.myexaminer.model.Account;
import com.myexaminer.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public void accountSave(Account account) {
        accountRepository.save(account);
    }

    public boolean accountExistsById(Long accountId) {
        Optional<Account> accountById = accountRepository.findById(accountId);

        return accountById.isPresent();
    }

    public boolean accountExistsByEmail(String email) {
        Optional<Account> accountByEmail = accountRepository.findByEmail(email);

        return accountByEmail.isPresent();
    }

    public boolean checkCredentials(Account account) {
        Optional<Account> accountFromDB = accountRepository.findByEmail(account.getEmail());

        if (accountFromDB.isEmpty()
                || !passwordEncoder.matches(account.getPassword(), accountFromDB.get().getPassword())) {
            return false;
        }

        return true;
    }

    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Account with email: " + email + " doesn't exist."));
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account with id: " + accountId + " doesn't exist."));
    }
}
