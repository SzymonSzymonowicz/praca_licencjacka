package com.myexaminer.security.service;

import com.myexaminer.model.Account;
import com.myexaminer.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AccountDetails implements UserDetails {
    // TODO think about other fields that may come useful on frontend
    private final Account account;

    private final Integer id;

    private final String email;

    private final List<GrantedAuthority> authorities;

    public static AccountDetails build(Account account) {
        List<GrantedAuthority> authorities = account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new AccountDetails(
                account,
                account.getId(),
                account.getEmail(),
                authorities
        );
    }

    public Integer getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // TODO email verification account.isVerificated
    @Override
    public boolean isEnabled() {
        return true;
    }
}
