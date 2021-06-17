package com.myexaminer.service;

import com.myexaminer.dto.ExamDTO;
import com.myexaminer.dto.GenericOneValue;
import com.myexaminer.dto.GenericTwoValues;
import com.myexaminer.entity.Exam;
import com.myexaminer.entity.TeachingGroup;
import com.myexaminer.enums.State;
import com.myexaminer.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final TeachingGroupService teachingGroupService;

    public Long saveExam(Exam exam) {
        return examRepository.save(exam).getId();
    }

    public boolean examExistsById(Long id) {
        Optional<Exam> examById = examRepository.findById(id);

        return examById.isPresent();
    }

    public Exam getExamById(Long id) {
        Optional<Exam> examById = examRepository.findById(id);

        return examById.orElseThrow(() -> new EntityNotFoundException("There is no Exam in database that you were looking for."));
    }

    public Iterable<Exam> returnAllExams() {
        return examRepository.findAll();
    }

    public Long createExam(ExamDTO examDTO) {
        Exam exam = Exam.mapExamDTOToExam(examDTO);
        exam.setStateToDraft();

        TeachingGroup teachingGroup = teachingGroupService.getTeachingGroupById(examDTO.getGroupId());
        exam.setTeachingGroup(teachingGroup);

        final Long persistedExamId = saveExam(exam);
        log.info("Exam with ID -> {} <- has been ADDED", exam.getId());

        return persistedExamId;
    }

    public List<ExamDTO> getExamDTOSByIdGroup(Long idGroup) {
        return StreamSupport.stream(returnAllExams().spliterator(), false).
                filter(exam -> exam.getTeachingGroup().getId() == idGroup).
                map(ExamDTO::new)
                .collect(Collectors.toList());
    }

    public State getState(GenericOneValue id) {
        return getExamById((Long) id.getFirstValue()).getState();
    }

    public void updateExamStatus(GenericTwoValues genericTwoValues) {
        Long id = (Long) genericTwoValues.getFirstValue();
        State state = State.valueOf((String) genericTwoValues.getSecondValue());

        Exam exam = getExamById(id);
        exam.setState(state);

        saveExam(exam);
    }

    public void updateExamStatus(Long examId, String newState) {
        State state = State.valueOf(newState);
        Exam exam = getExamById(examId);
        exam.setState(state);
        log.info("Change status of exam with id:{} to {}", examId, state);
        saveExam(exam);
    }

    public Iterable<ExamDTO> getExamDTOSByMyGroups(Long accountId) {
        List<TeachingGroup> groups = teachingGroupService.getTeachingGroupByAccountId(accountId);

        return groups.stream()
                .flatMap(group -> group.getExams().stream())
                .map(ExamDTO::new)
                .sorted(Comparator.comparing(dto -> dto.getAvailableFrom()))
                .collect(Collectors.toList());
    }

    public void updateExam(Long examId, ExamDTO updatedExamDTO) {
        Exam exam = getExamById(examId);
        TeachingGroup newGroup = teachingGroupService.getTeachingGroupById(updatedExamDTO.getGroupId());

        exam.setAvailableFrom(updatedExamDTO.getAvailableFrom());
        exam.setDuration(updatedExamDTO.getDuration());
        exam.setDescription(updatedExamDTO.getDescription());
        exam.setName(updatedExamDTO.getName());
        exam.setState(State.valueOf(updatedExamDTO.getState()));
        exam.setTeachingGroup(newGroup);

        System.out.println(updatedExamDTO);

        log.info("Exam with ID -> {} <- has been updated to new state: {}", examId, updatedExamDTO.getState());

        saveExam(exam);
    }
}
