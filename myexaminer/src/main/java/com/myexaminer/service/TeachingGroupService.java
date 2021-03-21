package com.myexaminer.service;

import com.myexaminer.model.Student;
import com.myexaminer.model.TeachingGroup;
import com.myexaminer.repository.TeachingGroupRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class TeachingGroupService {

    private final TeachingGroupRepository teachingGroupRepository;
    private final StudentService studentService;
    private LecturerService lecturerService;

    public TeachingGroupService(TeachingGroupRepository teachingGroupRepository, StudentService studentService, LecturerService lecturerService) {
        this.teachingGroupRepository = teachingGroupRepository;
        this.studentService = studentService;
        this.lecturerService = lecturerService;
    }

    public void teachingGroupSave(TeachingGroup teachingGroup) {
        teachingGroupRepository.save(teachingGroup);
    }

    public boolean teachingGroupExistsById(int idTeachingGroup) {
        Optional<TeachingGroup> teachingGroupById = teachingGroupRepository.findByidTeachingGroup(idTeachingGroup);

        return teachingGroupById.isPresent();
    }

    public TeachingGroup returnTeachingGroupById(int idTeachingGroup) {
        Optional<TeachingGroup> teachingGroup = teachingGroupRepository.findByidTeachingGroup(idTeachingGroup);

        return teachingGroup.get();
    }

    public boolean teachingGroupExistsByName(String teachingGroupName) {
        Optional<TeachingGroup> teachingGroupExistsByName = teachingGroupRepository.findByTeachingGroupName(teachingGroupName);

        return teachingGroupExistsByName.isPresent();
    }

    public void addStudentToTeachingGroup(int idTeachingGroup, int idStudent) {
        TeachingGroup teachingGroup = returnTeachingGroupById(idTeachingGroup);

        Student student = studentService.returnStudentById(idStudent);

        teachingGroup.addToUsers(student);

        teachingGroupSave(teachingGroup);

        student.addToTeachingGroups(teachingGroup);

        studentService.studentSave(student);
    }

    public List<TeachingGroup> returnTeachingGroupsByLecturerEmail(String email) {
        return teachingGroupRepository.findByLecturerAccountEmail(email);
    }

    public List<TeachingGroup> returnTeachingGroupsByLecturerId(int id) {
        return teachingGroupRepository.findByLecturerIdLecturer(id);
    }

    public void createTeachingGroup(TeachingGroup teachingGroup) {
        if (teachingGroupExistsById(teachingGroup.getIdTeachingGroup())) {
            log.info("Group with given ID -> {} <- ALREADY EXISTS", teachingGroup.getIdTeachingGroup());
            // throw EntityExistsException  --- > status conflict
            return;
        }

        if (!lecturerService.lecturerExistsById(teachingGroup.getLecturer().getIdLecturer())) {
            log.info("Lecturer with given ID -> {} <- DOES NOT EXIST", teachingGroup.getLecturer().getIdLecturer());
            return;
        }

        if (teachingGroupExistsByName(teachingGroup.getTeachingGroupName())) {
            log.info("Group with given name -> {} <- ALREADY EXISTS", teachingGroup.getTeachingGroupName());
            return;
        }

        teachingGroupSave(teachingGroup);
        log.info("Group with ID -> {} <- has been ADDED", teachingGroup.getIdTeachingGroup());
    }

    public void addStudentToGroup(int idTeachingGroup, int idStudent) {
        if (!teachingGroupExistsById(idTeachingGroup)) {
            log.info("Group with given ID -> {} <- DOES NOT EXIST", idTeachingGroup);
            return;
        }

        if (!studentService.studentExistsById(idStudent)) {
            log.info("Student with given ID -> {} <- DOES NOT EXIST", idStudent);
            return;
        }

        addStudentToTeachingGroup(idTeachingGroup, idStudent);
        log.info("Student with ID -> {} <- has been ADDED to TeachingGroup with ID -> {} <-", idStudent, idTeachingGroup);
    }
}
