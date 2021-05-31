package com.myexaminer.service;

import com.myexaminer.entity.Student;
import com.myexaminer.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private StudentService studentService;

    @Test
    void When_NoStudentByIdInRepository_Expect_EntityNotFoundException(){
        //given
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        //when //then
        assertThrows(EntityNotFoundException.class, () -> {
            studentService.getStudentById(1L);
        });
    }

    @Test
    void When_NoStudentByAccountIdInRepository_Expect_EntityNotFoundException(){
        //given
        when(studentRepository.findByAccountId(1L)).thenReturn(Optional.empty());

        //when //then
        assertThrows(EntityNotFoundException.class, () -> {
            studentService.getStudentByAccountId(1L);
        });
    }

    @Test
    void When_StudentExistsByIndex_Expect_True(){
        //given
        Student student = Student.builder().id(1L).build();

        when(studentRepository.findByIndex("123456")).thenReturn(Optional.of(student));

        //when
        boolean bool = studentService.studentExistsByIndex("123456");

        //then
        assertThat(bool).isTrue();
    }

    @Test
    void When_NoStudentByEmailInRepository_Expect_EntityNotFoundException(){
        //given
        when(studentRepository.findByAccountEmail("email")).thenReturn(Optional.empty());

        //when //then
        assertThrows(EntityNotFoundException.class, () -> {
            studentService.findStudentByEmail("email");
        });
    }
}
