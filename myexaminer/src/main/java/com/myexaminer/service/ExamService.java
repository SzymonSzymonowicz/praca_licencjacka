package com.myexaminer.service;

import com.myexaminer.model.Exam;
import com.myexaminer.model.TeachingGroup;
import com.myexaminer.modelDTO.ExamDTO;
import com.myexaminer.modelDTO.GenericOneValue;
import com.myexaminer.modelDTO.GenericTwoValues;
import com.myexaminer.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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

    public boolean examExistsById(int idExam) {
        Optional<Exam> examById = examRepository.findByIdExam(idExam);

        return examById.isPresent();
    }

    public Exam returnExamById(int idExam) {
        Optional<Exam> examById = examRepository.findByIdExam(idExam);

        return examById.orElseThrow(() -> new NoSuchElementException("There is no Exam in database that you were looking for."));
    }

    public Iterable<Exam> returnAllExams() {
        return examRepository.findAll();
    }

    public Exam getExam(Map<String, Integer> map_idExam) {
        Integer idExam = map_idExam.get("idExam");
        if (!examExistsById(idExam)) {
            log.info("Exam with given ID -> {} <- DOES NOT EXIST", idExam);
            return null;
        }

        Exam returnedExam = returnExamById(idExam);

        log.info("Exam with ID -> {} <- HAS BEEN RETURNED", returnedExam.getIdExam());

        return returnedExam;
    }

    public void createExam(ExamDTO examDTO, Integer id){
        Exam exam = Exam.mapExamDTOToExam(examDTO);
        exam.setStatusToDraft();

        TeachingGroup teachingGroup = teachingGroupService.getTeachingGroupById(id);
        exam.setTeachingGroup(teachingGroup);

        examSave(exam);
        log.info("Exam with ID -> {} <- has been ADDED", exam.getIdExam());
    }

    public List<ExamDTO> getExamDTOSByIdGroup(int idGroup) {
        return StreamSupport.stream(returnAllExams().spliterator(), false).
                filter(exam -> exam.getTeachingGroup().getIdTeachingGroup() == idGroup).
                map(ExamDTO::new).collect(Collectors.toList());
    }

    public Exam.Status getStatus(GenericOneValue idExam) {
        return returnExamById((Integer) idExam.getFirstValue()).getStatus();
    }

    public void updateExamStatus(GenericTwoValues genericTwoValues) {
        Integer idExam = (Integer) genericTwoValues.getFirstValue();
        Exam.Status status = Exam.Status.valueOf((String) genericTwoValues.getSecondValue());

        Exam exam = returnExamById(idExam);
        exam.setStatus(status);

        examSave(exam);
    }
}
