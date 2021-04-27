package com.myexaminer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CheckingExercisesServiceTest {

    @Mock
    private IndividualExamService individualExamService;

    @Mock
    private ExerciseService exerciseService;

    @Mock
    private AccountService accountService;

    @Mock
    private ArchiveExerciseService archiveExerciseService;

    @InjectMocks
    private CheckingExercisesService checkingExercisesService;


    @Test
    void Should_ReturnArchiveExercise_When_UserIsStudent(){


    }
}
