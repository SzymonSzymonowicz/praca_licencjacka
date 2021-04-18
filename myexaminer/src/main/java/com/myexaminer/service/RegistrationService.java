package com.myexaminer.service;

import com.myexaminer.model.Account;
import com.myexaminer.model.Notebook;
import com.myexaminer.model.Student;
import com.myexaminer.modelDTO.RegisterDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class RegistrationService {

    private final RoleService roleService;
    private final AccountService accountService;
    private final StudentService studentService;
    private final NotebookService notebookService;

    public RegistrationService(RoleService roleService, AccountService accountService, StudentService studentService, NotebookService notebookService){
        this.roleService = roleService;
        this.accountService = accountService;
        this.studentService = studentService;
        this.notebookService = notebookService;
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public void registerNewStudentToDatabase(RegisterDTO registerDTO){

        Account newAccount = new Account(
                registerDTO.getEmail(),
                passwordEncoder.encode(registerDTO.getPassword()),
                registerDTO.getRecoveryQuestion(),
                registerDTO.getRecoveryAnswer()
        );
        newAccount.addToRoles(roleService.returnRoleByName("STUDENT"));
        accountService.accountSave(newAccount);
        log.info("Account with ID -> {} <- has been CREATED and SAVED to database", newAccount.getAccountId());

        Student newStudent = new Student(
                newAccount.getAccountId(),
                registerDTO.getFirstName(),
                registerDTO.getLastName(),
                registerDTO.getIndex(),
                registerDTO.getFaculty(),
                registerDTO.getFieldOfStudy()
        );
        studentService.studentSave(newStudent);
        log.info("Student with ID -> {} <- has been CREATED and SAVED to database", newStudent.getIdStudent());

        Notebook newNotebok = new Notebook("Miłej nauki !", newAccount);
        notebookService.notebookSave(newNotebok);

        log.info("Notebook with ID -> {} <- has been CREATED and SAVED to database", newNotebok.getIdNotebook());
    }
}