package com.myexaminer.repository;

import com.myexaminer.model.Student;
import com.myexaminer.model.TeachingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeachingGroupRepository extends JpaRepository<TeachingGroup, Integer> {
    long deleteByidTeachingGroup(int idTeachingGroup);
    Optional<TeachingGroup> findByidTeachingGroup(int idTeachingGroup);
    Optional<TeachingGroup> findByTeachingGroupName(String teachingGroupName);
    Optional<TeachingGroup> findByStudentsContaining(Student student);
    List<TeachingGroup> findByLecturerAccountEmail(String email);
    List<TeachingGroup> findByLecturerIdLecturer(int id);
    Optional<TeachingGroup> findByAccessCode(String accessCode);
}