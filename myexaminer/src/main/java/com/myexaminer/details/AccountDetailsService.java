package com.myexaminer.details;

import com.myexaminer.model.Account;
import com.myexaminer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AccountDetailsService implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Account account = accountService.returnAccountByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not find account"));

        return new AccountDetails(account);
    }
}
