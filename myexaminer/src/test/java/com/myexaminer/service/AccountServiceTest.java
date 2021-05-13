package com.myexaminer.service;

import com.myexaminer.entity.Account;
import com.myexaminer.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private final String email = "testmail@gmail.com";

    @Test
    void When_AccountDoesNotExistById_Expect_False(){
        //given
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        //when
        boolean result = accountService.accountExistsById(1L);

        //then
        assertThat(result).isFalse();
    }

    @Test
    void When_AccountExistsById_Expect_True(){
        //given
        Account account = Account.builder()
                .id(1L)
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        //when
        boolean result = accountService.accountExistsById(1L);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void When_AccountDoesNotExistByEmail_Expect_False(){
        //given
        when(accountRepository.findByEmail(email)).thenReturn(Optional.empty());

        //when
        boolean result = accountService.accountExistsByEmail(email);

        //then
        assertThat(result).isFalse();
    }

    @Test
    void When_AccountExistsByEmail_Expect_True(){
        //given
        Account account = Account.builder()
                .email(email)
                .build();

        when(accountRepository.findByEmail(email)).thenReturn(Optional.of(account));

        //when
        boolean result = accountService.accountExistsByEmail(email);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void When_NoAccountByEmail_Throw_EntityNotFoundException(){
        //given
        when(accountRepository.findByEmail(email)).thenReturn(Optional.empty());

        //when //then
        assertThrows(EntityNotFoundException.class, () -> {
            accountService.getAccountByEmail(email);
        });
    }

    @Test
    void When_NoAccountById_Throw_EntityNotFoundException(){
        //given
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        //when //then
        assertThrows(EntityNotFoundException.class, () -> {
            accountService.getAccountById(1L);
        });
    }
}
