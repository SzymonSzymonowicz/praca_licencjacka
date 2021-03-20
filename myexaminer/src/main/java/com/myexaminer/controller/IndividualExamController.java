package com.myexaminer.controller;

import com.myexaminer.model.Exam;
import com.myexaminer.model.IndividualExam;
import com.myexaminer.model.Student;
import com.myexaminer.model.TeachingGroup;
import com.myexaminer.modelDTO.LecturerIndividualExamView;
import com.myexaminer.service.IndividualExamService;
import com.myexaminer.service.TeachingGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping(path="/individual-exams")
public class IndividualExamController {

    private final IndividualExamService individualExamService;
    private final TeachingGroupService teachingGroupService;

    public IndividualExamController(IndividualExamService individualExamService, TeachingGroupService teachingGroupService){
        this.individualExamService = individualExamService;
        this.teachingGroupService = teachingGroupService;
    }

    @GetMapping(path = "/group")
    @ResponseBody
    public List<LecturerIndividualExamView> getIndividualsFromLecturerGroup(HttpServletRequest request){
        List<TeachingGroup> teachingGroups = teachingGroupService.returnTeachingGroupsByLecturerId(1);
        System.out.println();
        List<LecturerIndividualExamView> individualExamViewList = new ArrayList<>();
        for(TeachingGroup teachingGroup: teachingGroups){
            Set<Exam> exams = teachingGroup.getExams();
            for (Exam exam: exams){
                for(Student student: teachingGroup.getStudents()) {
                    Optional<IndividualExam> individualExam = individualExamService.returnOptionalIndividualExamByIdStudentAndIdExam(student.getIdStudent(), exam.getIdExam());
                    if(individualExam.isPresent()) {
                        if (!individualExam.get().isChecked()) {
                            individualExamViewList.add(new LecturerIndividualExamView(
                                    individualExam.get().getIdIndividualExam(),
                                    exam.getExamName(),
                                    exam.getExamDescription(),
                                    exam.getExamAvailableDate(),
                                    teachingGroup.getIdTeachingGroup(),
                                    teachingGroup.getTeachingGroupName(),
                                    student.getIndex()
                            ));
                        }
                    }
                }
            }
        }

        return individualExamViewList;
    }
}
