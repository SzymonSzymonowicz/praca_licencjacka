package com.myexaminer.repository;

import com.myexaminer.dto.GroupNameAndId;
import com.myexaminer.entity.Lecturer;
import com.myexaminer.entity.Student;
import com.myexaminer.entity.TeachingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("SELECT new com.myexaminer.dto.GroupNameAndId(tg.id, tg.name)" +
            " FROM TeachingGroup tg " +
            "WHERE tg.lecturer = :lecturer")
    List<GroupNameAndId> getAllGroupsNameAndIdByLecturer(Lecturer lecturer);

    @Query("SELECT new com.myexaminer.dto.GroupNameAndId(tg.id, tg.name)" +
            " FROM TeachingGroup tg " +
            "WHERE :student MEMBER OF tg.students")
    List<GroupNameAndId> getAllGroupsNameAndIdByStudent(Student student);
}