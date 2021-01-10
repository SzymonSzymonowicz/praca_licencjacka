package com.myexaminer.service;

import com.myexaminer.model.ArchiveExercise;
import com.myexaminer.model.Exercise;
import com.myexaminer.model.Student;
import com.myexaminer.repository.ArchiveExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArchiveExerciseService {

    private final ArchiveExerciseRepository archiveExerciseRepository;

    public ArchiveExerciseService(ArchiveExerciseRepository archiveExerciseRepository){
        this.archiveExerciseRepository = archiveExerciseRepository;
    }

    public void exerciseSave(ArchiveExercise archiveExercise) {
        archiveExerciseRepository.save(archiveExercise);
    }

    public ArchiveExercise returnArchiveExerciseByExerciseAndStudent(Exercise exercise, Student student){
        Optional<ArchiveExercise> exerciseByExerciseAndStudent = archiveExerciseRepository.findByExerciseIdExerciseAndStudentIdStudent(exercise.getIdExercise(), student.getIdStudent());

        return exerciseByExerciseAndStudent.get();
    }

    public String toJSONArray(List<String> answers){
        StringBuilder jsonAnswer = new StringBuilder("{\"answer\": [");
        for (int i = 0; i < answers.size()-1; i++){
            jsonAnswer.append("\"");
            jsonAnswer.append(answers.get(i));
            jsonAnswer.append("\"");
            jsonAnswer.append(",");
        }
        jsonAnswer.append("\"");
        jsonAnswer.append(answers.get(answers.size()-1));
        jsonAnswer.append("\"]}");

        return jsonAnswer.toString();
    }

    public String toJSONString(String answer){
        StringBuilder jsonAnswer = new StringBuilder("{\"answer\": \"");
        jsonAnswer.append(answer);
        jsonAnswer.append("\"}");

        return jsonAnswer.toString();
    }
}
