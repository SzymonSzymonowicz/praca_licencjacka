package com.myexaminer.repository;

import com.myexaminer.entity.Lecturer;
import com.myexaminer.entity.Student;
import com.myexaminer.entity.TeachingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeachingGroupRepository extends JpaRepository<TeachingGroup, Long> {
    Optional<TeachingGroup> findByName(String name);

    Optional<TeachingGroup> findByStudentsContaining(Student student);

    List<TeachingGroup> findByLecturerAccountEmail(String email);

    List<TeachingGroup> findByLecturerId(Long id);

    Optional<TeachingGroup> findByAccessCode(String accessCode);

    List<TeachingGroup> findAllByStudents(Student student);

    List<TeachingGroup> findAllByLecturer(Lecturer lecturer);
}