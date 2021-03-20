package com.myexaminer.controller;

import com.myexaminer.model.Account;
import com.myexaminer.model.Role;
import com.myexaminer.modelDTO.RegisterDTO;
import com.myexaminer.service.*;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(path="/account")
public class AccountController {

    private final AccountService accountService;
    private final RegistrationService registrationService;
    private final StudentService studentService;

    public AccountController(AccountService accountService, RegistrationService registrationService, StudentService studentService) {
        this.accountService = accountService;
        this.registrationService = registrationService;
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addAccount(@RequestBody RegisterDTO registerDTO) {
        if(accountService.accountExistsByEmail(registerDTO.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        if(studentService.studentExistsByIndex(registerDTO.getIndex())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        registrationService.registerNewStudentToDatabase(registerDTO);

        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PostMapping(path = "/login")
    public ResponseEntity<?> login (@RequestBody Account account) {
        if(!accountService.checkCredentials(account)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok("Connection succeeded");
    }

    @GetMapping("/role")
    @ResponseBody
    public List<Role> role(HttpServletRequest request){
        return accountService.returnAccountByEmail(request.getUserPrincipal().getName()).getRoles();
    }
}
