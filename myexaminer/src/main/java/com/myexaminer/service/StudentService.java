package com.myexaminer.service;

import com.myexaminer.model.Student;
import com.myexaminer.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final AccountService accountService;

    public void studentSave(Student student) {
        studentRepository.save(student);
    }

    public boolean studentExistsById(Long id) {
        Optional<Student> userById = studentRepository.findById(id);

        return userById.isPresent();
    }

    public Student getStudentById(Long id) {
        Optional<Student> studentById = studentRepository.findById(id);

        return studentById.orElseThrow(() -> new EntityNotFoundException("Student with id " + id + "does not exist!"));
    }

    public Student getStudentByAccountId(Long accountId) {
        return studentRepository.findByAccountId(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Student of account id: " + accountId + "does not exist!"));
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
        if (!accountService.accountExistsById(student.getId())) {
            log.info("Account with given ID -> {} <- DOES NOT EXIST", student.getId());
            return;
        }
        if (studentExistsById(student.getId())) {
            log.info("Student with given ID -> {} <- ALREADY EXISTS", student.getId());
            return;
        }

        studentSave(student);
        log.info("Student with ID -> {} <- has been ADDED", student.getId());
    }

    public Student findStudentByEmail(String email) {
        return studentRepository.findByAccountEmail(email).orElseThrow(() -> new EntityNotFoundException("There is no student with given email -> " + email));
    }
}
