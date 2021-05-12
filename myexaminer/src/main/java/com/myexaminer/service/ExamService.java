package com.myexaminer.service;

import com.myexaminer.enums.State;
import com.myexaminer.model.Exam;
import com.myexaminer.model.TeachingGroup;
import com.myexaminer.modelDTO.ExamDTO;
import com.myexaminer.modelDTO.GenericOneValue;
import com.myexaminer.modelDTO.GenericTwoValues;
import com.myexaminer.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final TeachingGroupService teachingGroupService;

    public void examSave(Exam exam) {
        examRepository.save(exam);
    }

    public boolean examExistsById(Long id) {
        Optional<Exam> examById = examRepository.findById(id);

        return examById.isPresent();
    }

    public Exam returnExamById(Long id) {
        Optional<Exam> examById = examRepository.findById(id);

        return examById.orElseThrow(() -> new EntityNotFoundException("There is no Exam in database that you were looking for."));
    }

    public Iterable<Exam> returnAllExams() {
        return examRepository.findAll();
    }

    public Exam getExam(Map<String, Long> map_id) {
        Long id = map_id.get("id");
        if (!examExistsById(id)) {
            log.info("Exam with given ID -> {} <- DOES NOT EXIST", id);
            return null;
        }

        Exam returnedExam = returnExamById(id);

        log.info("Exam with ID -> {} <- HAS BEEN RETURNED", returnedExam.getId());

        return returnedExam;
    }

    public void createExam(ExamDTO examDTO, Long id) {
        Exam exam = Exam.mapExamDTOToExam(examDTO);
        exam.setStateToDraft();

        TeachingGroup teachingGroup = teachingGroupService.getTeachingGroupById(id);
        exam.setTeachingGroup(teachingGroup);

        examSave(exam);
        log.info("Exam with ID -> {} <- has been ADDED", exam.getId());
    }

    public List<ExamDTO> getExamDTOSByIdGroup(Long idGroup) {
        return StreamSupport.stream(returnAllExams().spliterator(), false).
                filter(exam -> exam.getTeachingGroup().getId() == idGroup).
                map(ExamDTO::new).collect(Collectors.toList());
    }

    public State getState(GenericOneValue id) {
        return returnExamById((Long) id.getFirstValue()).getState();
    }

    public void updateExamStatus(GenericTwoValues genericTwoValues) {
        Long id = (Long) genericTwoValues.getFirstValue();
        State state = State.valueOf((String) genericTwoValues.getSecondValue());

        Exam exam = returnExamById(id);
        exam.setState(state);

        examSave(exam);
    }
}
