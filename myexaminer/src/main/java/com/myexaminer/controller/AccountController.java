package com.myexaminer.controller;

import com.myexaminer.model.Account;
import com.myexaminer.model.Role;
import com.myexaminer.modelDTO.RegisterDTO;
import com.myexaminer.service.AccountService;
import com.myexaminer.service.RegistrationService;
import com.myexaminer.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
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
    public List<Role> role(HttpServletRequest request){
        return accountService.returnAccountByEmail(request.getUserPrincipal().getName()).getRoles();
    }
}
