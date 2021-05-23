package com.myexaminer.service;

import com.myexaminer.dto.LecturerIndividualExamView;
import com.myexaminer.entity.Exam;
import com.myexaminer.entity.IndividualExam;
import com.myexaminer.entity.Student;
import com.myexaminer.entity.TeachingGroup;
import com.myexaminer.repository.IndividualExamRepository;
import com.myexaminer.security.service.AccountDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class IndividualExamService {

    private final IndividualExamRepository individualExamRepository;
    private final StudentService studentService;
    private final ExamService examService;
    private final TeachingGroupService teachingGroupService;

    public void individualExamSave(IndividualExam individualExam) {
        individualExamRepository.save(individualExam);
    }

    public IndividualExam getIndividualExamById(Long id) {
        return individualExamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Individual exam of id: " + id + " was not found."));
    }

    public Optional<IndividualExam> getOptionalIndividualExamByStudentIdAndExamId(Long idStudent, Long idExam) {
        return individualExamRepository.findByStudentIdAndMainExamId(idStudent, idExam);
    }

    public IndividualExam returnIndividualExamByIdStudentAndIdExam(Long studentId, Long examId) {
        return getOptionalIndividualExamByStudentIdAndExamId(studentId, examId)
                .orElseThrow(() -> new EntityNotFoundException("There is no Individual Exam in database that you were looking for."));
    }

    public IndividualExam createOrGetIndividualExamAndReturn(Long idStudent, Long idExam) {
        Student student = studentService.getStudentById(idStudent);
        Exam exam = examService.getExamById(idExam);
        Optional<IndividualExam> individualExamOpt = getOptionalIndividualExamByStudentIdAndExamId(idStudent, idExam);
        IndividualExam individualExam;
        if (individualExamOpt.isEmpty()) {
            individualExam = new IndividualExam(exam, student);
            individualExamSave(individualExam);
        } else {
            individualExam = individualExamOpt.get();
        }

        return individualExam;
    }

    @Transactional
    public void setIndividualExamToChecked(Long id) {
        IndividualExam individualExam = getIndividualExamById(id);
        individualExam.setChecked(true);
        individualExamSave(individualExam);
    }

    public List<LecturerIndividualExamView> getLecturerIndividualExamViews(Authentication authentication) {
        AccountDetails details = (AccountDetails) authentication.getPrincipal();
        Long lecturerId = details.getId();

        List<TeachingGroup> teachingGroups = teachingGroupService.getTeachingGroupsByLecturerId(lecturerId);

        List<LecturerIndividualExamView> individualExamViewList = new ArrayList<>();

        teachingGroups.stream().forEach(teachingGroup -> {
            Set<Exam> exams = teachingGroup.getExams();
            exams.stream().forEach(exam -> {
                teachingGroup.getStudents().stream().forEach(student -> {
                    Optional<IndividualExam> individualExam = getOptionalIndividualExamByStudentIdAndExamId(student.getId(), exam.getId());
                    if (individualExam.isPresent()) {
                        if (!individualExam.get().isChecked()) {
                            individualExamViewList.add(new LecturerIndividualExamView(
                                    individualExam.get().getId(),
                                    exam.getName(),
                                    exam.getDescription(),
                                    exam.getAvailableFrom(),
                                    teachingGroup.getId(),
                                    teachingGroup.getName(),
                                    student.getIndex()
                            ));
                        }
                    }
                });
            });
        });

        return individualExamViewList;
    }
}
