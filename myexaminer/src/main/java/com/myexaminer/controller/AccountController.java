package com.myexaminer.controller;

import com.myexaminer.model.Account;
import com.myexaminer.model.Role;
import com.myexaminer.model.Student;
import com.myexaminer.modelDTO.RegisterDTO;
import com.myexaminer.service.AccountService;
import com.myexaminer.service.RoleService;
import com.myexaminer.service.StudentService;
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
    private final StudentService studentService;
    private final RoleService roleService;

    public AccountController(AccountService accountService, StudentService studentService, RoleService roleService) {
        this.accountService = accountService;
        this.studentService = studentService;
        this.roleService = roleService;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<HttpStatus> addNewAccount (@RequestBody RegisterDTO registerDTO) {
        if(accountService.accountExistsByEmail(registerDTO.getEmail())){
            log.info("Account with given email -> {} <- ALREADY EXISTS", registerDTO.getEmail());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
/*        if(accountService.accountExistsById(registerDTO.getIdAccount())){
            log.info("Account with given ID -> {} <- ALREADY EXISTS", registerDTO.getIdAccount());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }*/
        Account account = new Account(
                registerDTO.getEmail(),
                passwordEncoder.encode(registerDTO.getPassword()),
                registerDTO.getRecoveryQuestion(),
                registerDTO.getRecoveryAnswer()
        );

        Role role = roleService.returnRoleByName("STUDENT");
        account.addToRoles(role);

        accountService.accountSave(account);
        log.info("Account with ID -> {} <- has been ADDED", account.getIdAccount());

        Student student = new Student(
          account.getIdAccount(),
          registerDTO.getFirstName(),
          registerDTO.getLastName(),
          registerDTO.getIndex(),
          registerDTO.getFaculty(),
          registerDTO.getFieldOfStudy()
        );

        studentService.studentSave(student);
        log.info("Student with ID -> {} <- has been ADDED", student.getIdStudent());

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
