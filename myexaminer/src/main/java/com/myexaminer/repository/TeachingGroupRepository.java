package com.myexaminer.repository;

import com.myexaminer.model.TeachingGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeachingGroupRepository extends JpaRepository<TeachingGroup, Integer> {
    long deleteByidTeachingGroup(int idTeachingGroup);
    Optional<TeachingGroup> findByidTeachingGroup(int idTeachingGroup);
    Optional<TeachingGroup> findByTeachingGroupName(String teachingGroupName);
    List<TeachingGroup> findByLecturerAccountEmail(String email);
    List<TeachingGroup> findByLecturerIdLecturer(int id);
}