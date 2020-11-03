package com.myexaminer.service;

import com.myexaminer.model.Lecturer;
import com.myexaminer.repository.LecturerRepository;
import org.springframework.stereotype.Service;

@Service
public class LecturerService {
    private final LecturerRepository lecturerRepository;

    public LecturerService(LecturerRepository lecturerRepository){this.lecturerRepository = lecturerRepository;}

    public void lecturerSave(Lecturer lecturer) { lecturerRepository.save(lecturer); }
}
