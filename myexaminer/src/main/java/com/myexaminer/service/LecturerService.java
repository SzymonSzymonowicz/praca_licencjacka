package com.myexaminer.service;

import com.myexaminer.model.Account;
import com.myexaminer.model.Lecturer;
import com.myexaminer.model.Student;
import com.myexaminer.repository.LecturerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LecturerService {
    private final LecturerRepository lecturerRepository;

    public LecturerService(LecturerRepository lecturerRepository){this.lecturerRepository = lecturerRepository;}

    public void lecturerSave(Lecturer lecturer) { lecturerRepository.save(lecturer); }

    public boolean lecturerExistsById(int idLecturer){
        Optional<Lecturer> lecturerById = lecturerRepository.findByIdLecturer(idLecturer);

        return lecturerById.isPresent();
    }

    public Lecturer returnLecturerById(int idLecturer){
        Optional<Lecturer> lecturerById = lecturerRepository.findByIdLecturer(idLecturer);

        return lecturerById.get();
    }
}
