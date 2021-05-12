package com.myexaminer.service;

import com.myexaminer.model.Lecturer;
import com.myexaminer.repository.LecturerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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

    public boolean lecturerExistsById(Long id) {
        Optional<Lecturer> lecturerById = lecturerRepository.findById(id);

        return lecturerById.isPresent();
    }

    public Lecturer returnLecturerById(Long id) {
        Optional<Lecturer> lecturerById = lecturerRepository.findById(id);

        return lecturerById.orElseThrow(() -> new EntityNotFoundException("There is no Lecturer in database that you were looking for."));
    }

    public void createLecturer(Lecturer lecturer) {
        if (!accountService.accountExistsById(lecturer.getId())) {
            log.info("Account with given ID -> {} <- DOES NOT EXIST", lecturer.getId());
            return;
        }
        if (lecturerExistsById(lecturer.getId())) {
            log.info("Lecturer with given ID -> {} <- ALREADY EXISTS", lecturer.getId());
            return;
        }

        lecturerSave(lecturer);
        log.info("Lecturer with ID -> {} <- has been ADDED", lecturer.getId());
    }

    public Lecturer findLecturerByEmail(String email) {
        return lecturerRepository.findByAccount_Email(email).orElseThrow(() -> new EntityNotFoundException("There is no lecturer with email -> " + email));
    }

    public Lecturer getLecturerByAccountId(Long accountId) {
        return lecturerRepository.findByAccountId(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Lecturer of account id: " + accountId + "does not exist!"));
    }
}
