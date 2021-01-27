package com.myexaminer.controller;

import com.myexaminer.model.Account;
import com.myexaminer.model.Role;
import com.myexaminer.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Log4j2
@Controller
@RequestMapping(path="/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<HttpStatus> addNewAccount (@RequestBody Account account) {
        if(accountService.accountExistsByEmail(account.getEmail())){
            log.info("Account with given email -> {} <- ALREADY EXISTS", account.getEmail());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        if(accountService.accountExistsById(account.getIdAccount())){
            log.info("Account with given ID -> {} <- ALREADY EXISTS", account.getIdAccount());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        accountService.accountSave(account);
        log.info("Account with ID -> {} <- has been ADDED", account.getIdAccount());

        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PostMapping(path = "/login")
    public ResponseEntity<?> login (@RequestBody Account account) {
        if(!accountService.checkCredentials(account)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok("Connection succeeded");
    }

    @GetMapping("/getRole")
    @ResponseBody
    public List<Role> role(HttpServletRequest request){
        return accountService.returnAccountByEmail(request.getUserPrincipal().getName()).get().getRoles();
    }
}
