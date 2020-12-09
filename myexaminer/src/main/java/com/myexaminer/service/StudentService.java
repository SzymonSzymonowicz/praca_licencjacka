package com.myexaminer.service;

import com.myexaminer.model.Student;
import com.myexaminer.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
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

    public Iterable<Student> returnAllStudents(){
        return studentRepository.findAll();
    }
}
