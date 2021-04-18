package com.myexaminer.service;

import com.myexaminer.controller.AccountController;
import com.myexaminer.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

//    @Mock
//    private AccountService accountService;
//
//    @InjectMocks
//    AccountController accountController;
//
//    private Account account1, account2;

/*    @BeforeEach
    void init() {
        account1 = new Account("testowy@gmail.com", "haslo", "Tak?", "Nie");
        account2 = new Account("testowy@gmail.com", "haslo", "Tak?", "Nie");
        account1.setAccountId(1);
        account2.setAccountId(2);
    }*/

/*    @Test
    public void When_addNewAccount_expect_accountService_called() {
        final ArgumentCaptor<Account> argumentCaptor = ArgumentCaptor.forClass(Account.class);
        accountController.addNewAccount(account1);
        verify(accountService).accountSave(argumentCaptor.capture());
        Assertions.assertEquals("testowy@gmail.com", argumentCaptor.getValue().getEmail());
        Assertions.assertEquals("haslo", argumentCaptor.getValue().getPassword());
        Assertions.assertEquals("Tak?", argumentCaptor.getValue().getRecoveryQuestion());
        Assertions.assertEquals("Nie", argumentCaptor.getValue().getRecoveryAnswer());
    }*/

/*    @Test
    public void When_add_two_accounts_with_same_email_then_return_httpstatus_UNPROCESSABLE_ENTITY() {
        accountController.addNewAccount(account1);
        when(accountController.addNewAccount(account2)).thenReturn(new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY));
    }*/

}
