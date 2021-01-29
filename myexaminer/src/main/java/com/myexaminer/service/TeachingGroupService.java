package com.myexaminer.service;

import com.myexaminer.model.Student;
import com.myexaminer.model.TeachingGroup;
import com.myexaminer.repository.TeachingGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeachingGroupService {

    private final TeachingGroupRepository teachingGroupRepository;
    private final StudentService studentService;

    public TeachingGroupService(TeachingGroupRepository teachingGroupRepository, StudentService studentService){
        this.teachingGroupRepository = teachingGroupRepository;
        this.studentService = studentService;
    }

    public void teachingGroupSave(TeachingGroup teachingGroup){
        teachingGroupRepository.save(teachingGroup);}

    public boolean teachingGroupExistsById(int idTeachingGroup){
        Optional<TeachingGroup> teachingGroupById = teachingGroupRepository.findByidTeachingGroup(idTeachingGroup);

        return teachingGroupById.isPresent();
    }

    public TeachingGroup returnTeachingGroupById(int idTeachingGroup){
        Optional<TeachingGroup> teachingGroup = teachingGroupRepository.findByidTeachingGroup(idTeachingGroup);

        return teachingGroup.get();
    }

    public boolean teachingGroupExistsByName(String teachingGroupName){
        Optional<TeachingGroup> teachingGroupExistsByName = teachingGroupRepository.findByTeachingGroupName(teachingGroupName);

        return teachingGroupExistsByName.isPresent();
    }

    public void addStudentToTeachingGroup(int idTeachingGroup, int idStudent){
        TeachingGroup teachingGroup = returnTeachingGroupById(idTeachingGroup);

        Student student = studentService.returnStudentById(idStudent);

        teachingGroup.addToUsers(student);

        teachingGroupSave(teachingGroup);

        student.addToTeachingGroups(teachingGroup);

        studentService.studentSave(student);
    }

    public List<TeachingGroup> returnTeachingGroupsByLecturerEmail(String email){
        return teachingGroupRepository.findByLecturerAccountEmail(email);
    }

    public List<TeachingGroup> returnTeachingGroupsByLecturerId(int id){
        return teachingGroupRepository.findByLecturerIdLecturer(id);
    }
}
