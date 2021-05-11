package com.myexaminer.service;

import com.myexaminer.model.Exam;
import com.myexaminer.model.IndividualExam;
import com.myexaminer.model.Student;
import com.myexaminer.model.TeachingGroup;
import com.myexaminer.modelDTO.LecturerIndividualExamView;
import com.myexaminer.repository.IndividualExamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class IndividualExamService {

    private final IndividualExamRepository individualExamRepository;
    private final StudentService studentService;
    private final ExamService examService;
    private final TeachingGroupService teachingGroupService;

    public IndividualExamService(IndividualExamRepository individualExamRepository, StudentService studentService, ExamService examService, TeachingGroupService teachingGroupService) {
        this.individualExamRepository = individualExamRepository;
        this.studentService = studentService;
        this.examService = examService;
        this.teachingGroupService = teachingGroupService;
    }

    public void individualExamSave(IndividualExam individualExam) {
        individualExamRepository.save(individualExam);
    }

    public IndividualExam returnIndividualExamById(Long idIndividualExam) {
        return individualExamRepository.findByIdIndividualExam(idIndividualExam);
    }

    public Optional<IndividualExam> returnOptionalIndividualExamByIdStudentAndIdExam(Long idStudent, Long idExam) {
        return individualExamRepository.findByStudentIdStudentAndMainExamIdExam(idStudent, idExam);
    }

    public IndividualExam returnIndividualExamByIdStudentAndIdExam(Long idStudent, Long idExam) {
        return returnOptionalIndividualExamByIdStudentAndIdExam(idStudent, idExam)
                .orElseThrow(() -> new NoSuchElementException("There is no Individual Exam in database that you were looking for."));
    }

    public IndividualExam createOrGetIndividualExamAndReturn(Long idStudent, Long idExam) {
        Student student = studentService.getStudentById(idStudent);
        Exam exam = examService.returnExamById(idExam);
        Optional<IndividualExam> individualExamOpt = returnOptionalIndividualExamByIdStudentAndIdExam(idStudent, idExam);
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
    public void setIndividualExamToChecked(Long idIndividualExam) {
        IndividualExam individualExam = returnIndividualExamById(idIndividualExam);
        individualExam.setChecked(true);
        individualExamSave(individualExam);
    }

    public List<LecturerIndividualExamView> getLecturerIndividualExamViews(Long lecturerId) {
        List<TeachingGroup> teachingGroups = teachingGroupService.getTeachingGroupsByLecturerId(lecturerId);

        List<LecturerIndividualExamView> individualExamViewList = new ArrayList<>();

        teachingGroups.stream().forEach(teachingGroup -> {
            Set<Exam> exams = teachingGroup.getExams();
            exams.stream().forEach(exam -> {
                teachingGroup.getStudents().stream().forEach(student -> {
                    Optional<IndividualExam> individualExam = returnOptionalIndividualExamByIdStudentAndIdExam(student.getIdStudent(), exam.getIdExam());
                    if (individualExam.isPresent()) {
                        if (!individualExam.get().isChecked()) {
                            individualExamViewList.add(new LecturerIndividualExamView(
                                    individualExam.get().getIdIndividualExam(),
                                    exam.getExamName(),
                                    exam.getDescription(),
                                    exam.getAvailableDate(),
                                    teachingGroup.getIdTeachingGroup(),
                                    teachingGroup.getName(),
                                    student.getIndex()
                            ));
                        }
                    }
                });
            });
        });
//        for (TeachingGroup teachingGroup : teachingGroups) {
//            Set<Exam> exams = teachingGroup.getExams();
//            for (Exam exam : exams) {
//                for (Student student : teachingGroup.getStudents()) {
//                    Optional<IndividualExam> individualExam = returnOptionalIndividualExamByIdStudentAndIdExam(student.getIdStudent(), exam.getIdExam());
//                    if (individualExam.isPresent()) {
//                        if (!individualExam.get().isChecked()) {
//                            individualExamViewList.add(new LecturerIndividualExamView(
//                                    individualExam.get().getIdIndividualExam(),
//                                    exam.getExamName(),
//                                    exam.getExamDescription(),
//                                    exam.getAvailableDate(),
//                                    teachingGroup.getIdTeachingGroup(),
//                                    teachingGroup.getName(),
//                                    student.getIndex()
//                            ));
//                        }
//                    }
//                }
//            }
//        }

        return individualExamViewList;
    }
}
