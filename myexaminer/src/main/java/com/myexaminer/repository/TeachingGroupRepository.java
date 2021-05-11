package com.myexaminer.repository;

import com.myexaminer.model.Lecturer;
import com.myexaminer.model.Student;
import com.myexaminer.model.TeachingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeachingGroupRepository extends JpaRepository<TeachingGroup, Long> {
    long deleteByidTeachingGroup(Long idTeachingGroup);
    Optional<TeachingGroup> findByidTeachingGroup(Long idTeachingGroup);
    Optional<TeachingGroup> findByName(String name);
    Optional<TeachingGroup> findByStudentsContaining(Student student);
    List<TeachingGroup> findByLecturerAccountEmail(String email);
    List<TeachingGroup> findByLecturerIdLecturer(Long id);
    Optional<TeachingGroup> findByAccessCode(String accessCode);
    List<TeachingGroup> findAllByStudents(Student student);
    List<TeachingGroup> findAllByLecturer(Lecturer lecturer);
}