package com.myexaminer.controller;

import com.myexaminer.model.User;
import com.myexaminer.service.AccountService;
import com.myexaminer.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping(path="/user")
public class UserController {
    private final UserService userService;
    private final AccountService accountService;

    public UserController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addNewUser (@RequestBody User user) {
        if(!accountService.accountExistsById(user.getIdUser())) {
            log.info("Account with given idaccount -> {} <- DOES NOT EXIST", user.getIdUser());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if(userService.userExistsById(user)){
            log.info("User with given idaccount -> {} <- ALREADY EXISTS", user.getIdUser());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        userService.userSave(user);
        log.info("User with account_idaccount -> {} <- has been ADDED", user.getIdUser());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
