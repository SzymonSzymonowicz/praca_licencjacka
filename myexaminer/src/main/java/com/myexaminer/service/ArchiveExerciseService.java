package com.myexaminer.service;

import com.myexaminer.model.ArchiveExercise;
import com.myexaminer.model.Exercise;
import com.myexaminer.model.Student;
import com.myexaminer.modelDTO.ArchiveExerciseDTO;
import com.myexaminer.repository.ArchiveExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArchiveExerciseService {

    private final ArchiveExerciseRepository archiveExerciseRepository;
    private final ExamService examService;
    private final StudentService studentService;

    public ArchiveExerciseService(ArchiveExerciseRepository archiveExerciseRepository, ExamService examService, StudentService studentService){
        this.archiveExerciseRepository = archiveExerciseRepository;
        this.examService = examService;
        this.studentService = studentService;
    }

    public void exerciseSave(ArchiveExercise archiveExercise) {
        archiveExerciseRepository.save(archiveExercise);
    }

    public Optional<ArchiveExercise> returnArchiveExerciseByExerciseAndStudent(Exercise exercise, Student student){
        Optional<ArchiveExercise> exerciseByExerciseAndStudent = archiveExerciseRepository.findByExerciseIdExerciseAndStudentIdStudent(exercise.getIdExercise(), student.getIdStudent());

        return exerciseByExerciseAndStudent;
    }

    public List<ArchiveExerciseDTO> archiveExercisesDTOByExamIdAndIdStudent(int idExam, int idStudent){
        List<ArchiveExercise> archiveExerciseList = new ArrayList<>();
        Student student = studentService.returnStudentById(idStudent);
        for (Exercise exercise : examService.returnExamById(idExam).getExercises()) {
            archiveExerciseList.add(
                    returnArchiveExerciseByExerciseAndStudent(exercise, student).get()
            );
        }

        return  archiveExerciseList.stream().map(exercise -> new ArchiveExerciseDTO((exercise))).collect(Collectors.toList());
    }

    public String toJSONArray(List<String> answers){
        StringBuilder jsonAnswer = new StringBuilder("{\"answerJSON\":[");
        for (int i = 0; i < answers.size()-1; i++){
            jsonAnswer.append("\"");
            if (answers.get(i) != null) {
                jsonAnswer.append(answers.get(i));
            }
            jsonAnswer.append("\"");
            jsonAnswer.append(",");
        }
        jsonAnswer.append("\"");
        jsonAnswer.append(answers.get(answers.size()-1));
        jsonAnswer.append("\"]}");

        return jsonAnswer.toString();
    }

    public String toJSONString(String answer){
        StringBuilder jsonAnswer = new StringBuilder("{\"answerJSON\":\"");
        jsonAnswer.append(answer);
        jsonAnswer.append("\"}");

        return jsonAnswer.toString();
    }
}
