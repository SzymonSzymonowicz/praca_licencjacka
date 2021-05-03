package com.myexaminer.service;

import com.myexaminer.model.Student;
import com.myexaminer.repository.StudentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private AccountService accountService;

    public StudentService(StudentRepository studentRepository, AccountService accountService) {
        this.studentRepository = studentRepository;
        this.accountService = accountService;
    }

    public void studentSave(Student student) {
        studentRepository.save(student);
    }

    public boolean studentExistsById(int idStudent) {
        Optional<Student> userById = studentRepository.findByIdStudent(idStudent);

        return userById.isPresent();
    }

    public Student getStudentById(int idStudent) {
        Optional<Student> studentById = studentRepository.findByIdStudent(idStudent);

        return studentById.orElseThrow(() -> new EntityNotFoundException("Student with id " + idStudent + "does not exist!"));
    }

    public boolean studentExistsByIndex(String index) {
        Optional<Student> accountByEmail = studentRepository.findByIndex(index);

        if (accountByEmail.isPresent()) {
            log.info("Student with given index -> {} <- ALREADY EXISTS", index);
            return true;
        } else {
            return false;
        }
    }

    public Iterable<Student> returnAllStudents() {
        return studentRepository.findAll();
    }

    public void createStudent(Student student) {
        if (!accountService.accountExistsById(student.getIdStudent())) {
            log.info("Account with given ID -> {} <- DOES NOT EXIST", student.getIdStudent());
            return;
        }
        if (studentExistsById(student.getIdStudent())) {
            log.info("Student with given ID -> {} <- ALREADY EXISTS", student.getIdStudent());
            return;
        }

        studentSave(student);
        log.info("Student with ID -> {} <- has been ADDED", student.getIdStudent());
    }

    public Student findStudentByEmail(String email){
        return studentRepository.findByAccountEmail(email).orElseThrow(() -> new NoSuchElementException("There is no student with given email -> " + email));
    }
}
