package com.myexaminer.service;

import com.myexaminer.model.TeachingGroup;
import com.myexaminer.repository.TeachingGroupRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeachingGroupService {

    private final TeachingGroupRepository teachingGroupRepository;

    public TeachingGroupService(TeachingGroupRepository teachingGroupRepository){this.teachingGroupRepository = teachingGroupRepository;}

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
}
