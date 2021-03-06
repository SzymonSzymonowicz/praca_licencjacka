package com.myexaminer.service;

import com.myexaminer.model.Student;
import com.myexaminer.repository.StudentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){this.studentRepository = studentRepository;}

    public void studentSave(Student student) { studentRepository.save(student); }

    public boolean studentExistsById(int idStudent){
        Optional<Student> userById = studentRepository.findByIdStudent(idStudent);

        return userById.isPresent();
    }

    public Student returnStudentById(int idStudent){
        Optional<Student> studentById = studentRepository.findByIdStudent(idStudent);

        return studentById.get();
    }

    public boolean studentExistsByIndex(String index){
        Optional<Student> accountByEmail = studentRepository.findByIndex(index);

        if(accountByEmail.isPresent()){
            log.info("Student with given index -> {} <- ALREADY EXISTS", index);
            return true;
        }
        else {
            return false;
        }
    }

    public Iterable<Student> returnAllStudents(){
        return studentRepository.findAll();
    }
}
