package com.myexaminer.service;

import com.myexaminer.model.Lecturer;
import com.myexaminer.repository.LecturerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@Service
public class LecturerService {
    private final LecturerRepository lecturerRepository;
    private AccountService accountService;

    public LecturerService(LecturerRepository lecturerRepository, AccountService accountService) {
        this.lecturerRepository = lecturerRepository;
        this.accountService = accountService;
    }

    public void lecturerSave(Lecturer lecturer) {
        lecturerRepository.save(lecturer);
    }

    public boolean lecturerExistsById(int idLecturer) {
        Optional<Lecturer> lecturerById = lecturerRepository.findByIdLecturer(idLecturer);

        return lecturerById.isPresent();
    }

    public Lecturer returnLecturerById(int idLecturer) {
        Optional<Lecturer> lecturerById = lecturerRepository.findByIdLecturer(idLecturer);

        return lecturerById.orElseThrow(() -> new NoSuchElementException("There is no Lecturer in database that you were looking for."));
    }

    public void createLecturer(Lecturer lecturer) {
        if (!accountService.accountExistsById(lecturer.getIdLecturer())) {
            log.info("Account with given ID -> {} <- DOES NOT EXIST", lecturer.getIdLecturer());
            return;
        }
        if (lecturerExistsById(lecturer.getIdLecturer())) {
            log.info("Lecturer with given ID -> {} <- ALREADY EXISTS", lecturer.getIdLecturer());
            return;
        }

        lecturerSave(lecturer);
        log.info("Lecturer with ID -> {} <- has been ADDED", lecturer.getIdLecturer());
    }

    public Lecturer findLecturerByEmail(String email){
        return lecturerRepository.findByAccount_Email(email).orElseThrow(() -> new NoSuchElementException("There is no lecturer with email -> " + email));
    }

    public Lecturer getLecturerByAccountId(int accountId) {
        return lecturerRepository.findByAccountId(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Lecturer of account id: " + accountId + "does not exist!"));
    }
}
