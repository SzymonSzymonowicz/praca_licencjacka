package com.myexaminer.service;

import com.myexaminer.enums.RoleEnum;
import com.myexaminer.model.Account;
import com.myexaminer.model.Lecturer;
import com.myexaminer.model.Role;
import com.myexaminer.model.Student;
import com.myexaminer.model.TeachingGroup;
import com.myexaminer.modelDTO.AccessCodeDTO;
import com.myexaminer.modelDTO.TeachingGroupDTO;
import com.myexaminer.repository.TeachingGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class TeachingGroupService {

    private final TeachingGroupRepository teachingGroupRepository;
    private final StudentService studentService;
    private final LecturerService lecturerService;
    private final AccountService accountService;

    public void teachingGroupSave(TeachingGroup teachingGroup) {
        teachingGroupRepository.save(teachingGroup);
    }

    public boolean teachingGroupExistsById(int idTeachingGroup) {
        Optional<TeachingGroup> teachingGroupById = teachingGroupRepository.findByidTeachingGroup(idTeachingGroup);

        return teachingGroupById.isPresent();
    }

    public List<TeachingGroup> findAllGroups(){
        return teachingGroupRepository.findAll();
    }

    public TeachingGroup getTeachingGroupById(int idTeachingGroup) {
        Optional<TeachingGroup> teachingGroup = teachingGroupRepository.findByidTeachingGroup(idTeachingGroup);

        return teachingGroup.orElseThrow(() -> new EntityNotFoundException("Group with id " + idTeachingGroup + " does not exist!"));
    }

    public TeachingGroup getTeachingGroupByStudentId(int studentId) {
        Student student = studentService.getStudentById(studentId);

        return teachingGroupRepository
                .findByStudentsContaining(student)
                .orElseThrow(() ->
                        new EntityNotFoundException("There is no group containing student with id " + studentId  + "."));
    }

    public boolean teachingGroupExistsByName(String teachingGroupName) {
        Optional<TeachingGroup> teachingGroupExistsByName = teachingGroupRepository.findByName(teachingGroupName);

        return teachingGroupExistsByName.isPresent();
    }

    public void addStudentToTeachingGroup(int idTeachingGroup, int idStudent) {
        TeachingGroup teachingGroup = getTeachingGroupById(idTeachingGroup);

        Student student = studentService.getStudentById(idStudent);

        teachingGroup.addStudent(student);

        teachingGroupSave(teachingGroup);

        student.addToTeachingGroups(teachingGroup);

        studentService.studentSave(student);
    }

    public List<TeachingGroup> returnTeachingGroupsByLecturerEmail(String email) {
        return teachingGroupRepository.findByLecturerAccountEmail(email);
    }

    public List<TeachingGroup> getTeachingGroupsByLecturerId(int id) {
        return teachingGroupRepository.findByLecturerIdLecturer(id);
    }

    public void createTeachingGroup(TeachingGroupDTO teachingGroupDTO, Authentication authentication) {
        if (teachingGroupExistsByName(teachingGroupDTO.getName())) {
            log.info("Group with given name -> {} <- ALREADY EXISTS", teachingGroupDTO.getName());
            throw new EntityExistsException("Group with given name ->" + teachingGroupDTO.getName() + "<- ALREADY EXISTS");
        }

        Lecturer lecturer = lecturerService.findLecturerByEmail(authentication.getName());

        TeachingGroup teachingGroup = TeachingGroup.builder()
                .name(teachingGroupDTO.getName())
                .startingDate(LocalDateTime.now())
                .accessCode(RandomStringUtils.randomAlphanumeric(8))
                .lecturer(lecturer)
                .build();

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

    public void removeStudentFromGroup(int groupId, int studentId) {
        TeachingGroup group = getTeachingGroupById(groupId);
        group.removeStudent(studentService.getStudentById(studentId));
        teachingGroupRepository.save(group);
    }

    public void deleteGroup(int groupId) {
        teachingGroupRepository.deleteById(groupId);
        log.info("TeachingGroup with ID -> {} <- has been deleted", groupId);
    }

    public TeachingGroup findTeachingGroupByAccessCode(String code){
        TeachingGroup teachingGroup = teachingGroupRepository.getOne(1);
        return teachingGroupRepository.findByAccessCode(teachingGroup.getAccessCode()).orElseThrow(() -> new NoSuchElementException("There is no teaching group with given code -> " + code));
    }

    public ResponseEntity addStudentToGroupByCode(String accessCode, Authentication authentication){
        TeachingGroup teachingGroup = findTeachingGroupByAccessCode(accessCode);
        Student student = studentService.findStudentByEmail(authentication.getName());

        teachingGroup.addStudent(student);
        teachingGroupRepository.save(teachingGroup);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Student " + authentication.getName() + " has been added to teaching group");
    }

    public List<TeachingGroup> getTeachingGroupByAccountId(Integer accountId) {
        Account account = accountService.getAccountById(accountId);

        if (account.hasRole(RoleEnum.ROLE_STUDENT)) {
            Student student = studentService.getStudentByAccountId(account.getId());

            return teachingGroupRepository.findAllByStudents(student);
        } else if (account.hasRole(RoleEnum.ROLE_LECTURER)) {
            Lecturer lecturer = lecturerService.getLecturerByAccountId(account.getId());

            return teachingGroupRepository.findAllByLecturer(lecturer);
        }

        throw new EntityNotFoundException("Your account doesn't posses required roles. Contact administrators of the application.");
    }
}
