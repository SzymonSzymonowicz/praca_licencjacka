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
}
