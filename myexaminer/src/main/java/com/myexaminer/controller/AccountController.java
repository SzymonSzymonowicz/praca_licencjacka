package com.myexaminer.controller;

import com.myexaminer.model.Account;
import com.myexaminer.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@Controller
@RequestMapping(path="/account")
public class AccountController {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(AccountController.class);
    @Autowired(required = true)
    private AccountService accountService;

/*    public AccountController(AccountService accountService) { this.accountService = accountService; }*/

    @PostMapping(path="/add")
    public @ResponseBody void addNewAccount (@RequestBody Account account) {
    accountService.accountSave(account);
    log.info("Account with idaccount -> {} <- has been ADDED", account.getIdAccount());
    }

}
