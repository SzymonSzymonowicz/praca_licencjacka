package com.myexaminer.controller;

import com.myexaminer.model.Student;
import com.myexaminer.service.AccountService;
import com.myexaminer.service.StudentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
@RequestMapping(path="/student")
public class StudentController {
    private final StudentService studentService;
    private final AccountService accountService;

    public StudentController(StudentService studentService, AccountService accountService) {
        this.studentService = studentService;
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addNewStudent (@RequestBody Student student) {
        if(!accountService.accountExistsById(student.getIdStudent())) {
            log.info("Account with given ID -> {} <- DOES NOT EXIST", student.getIdStudent());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if(studentService.studentExistsById(student.getIdStudent())){
            log.info("Student with given ID -> {} <- ALREADY EXISTS", student.getIdStudent());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        studentService.studentSave(student);
        log.info("Student with ID -> {} <- has been ADDED", student.getIdStudent());
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @GetMapping("/all")
    public @ResponseBody Iterable<Student> getStudents() {
        return studentService.returnAllStudents();
    }

}
