import React from 'react';
import { useForm } from "react-hook-form";
import { Box, Button } from "@material-ui/core";
import styles from "components/group/group.module.css";
import { addExerciseToExamUrl, lessonIdUrl } from "router/urls";
import authHeader from "services/auth-header";
import CheckIcon from '@material-ui/icons/Check';
import CloseIcon from '@material-ui/icons/Close';
import BlanksTaskFormModule from './BlanksTaskFormModule';
import ClosedTaskFormModule from './ClosedTaskFormModule';
import TaskTypeEnum from './TaskTypeEnum';
import BaseTaskFormModule from "./BaseTaskFormModule";
import { useParams } from 'react-router';

export default function TaskForm(props) {
  const { getExam, mode, task, type, resetEdited } = props;
  let { examId } = useParams();

  const { control, formState: { errors }, reset, handleSubmit, setValue, getValues } = useForm({
    defaultValues: task || {
      type: type,
      instruction: "",
      points: 1,
      fill: "",
      answers: [
        { text: "", value: "T" },
        { text: "", value: "F" },
        { text: "", value: "F" },
        { text: "", value: "F" }
      ]
    }
  });

  let module;
  
  switch (type) {
    case TaskTypeEnum.BLANKS:
      module = <BlanksTaskFormModule control={control} errors={errors} />;
      break;
    case TaskTypeEnum.CLOSED:
      module = <ClosedTaskFormModule control={control} errors={errors} getValues={getValues} setValue={setValue} />;
      break;
    case TaskTypeEnum.OPEN:
    default:
      <></>
  }

  const addTaskToExam = (examId, exercise) => {
    console.log(exercise);
    fetch(addExerciseToExamUrl(examId), {
      method: "POST",
      headers: {
        ...authHeader(),
        "Content-Type": "application/json"
      },
      body: JSON.stringify(exercise)
    })
      .then(res => {
        if (res.status === 200) {
          //callback to refresh parent
          getExam();
          reset();
        }
        else {
          console.log("Adding exercise failed");
          console.log(res.text())
        }
    })
    .catch(err => { console.error(err) })
  }

  const editTask = (lessonId, lesson) => {
    fetch(lessonIdUrl(lessonId), {
      method: "PATCH",
      headers: {
        ...authHeader(),
        "Content-Type": "application/json"
      },
      body: JSON.stringify(lesson)
    })
      .then(res => {
        if (res.status === 200) {
          props?.getGroup(examId);
          
          if (typeof resetEdited === "function") {
            resetEdited();
          }
        }
        else {
          console.log("Editing lesson failed");
          console.log(res.text())
        }
    })
    .catch(err => { console.error(err) })
  }

  let action;
  let submitText;
  
  switch (mode) {
    case 'create':
      action = (task) => addTaskToExam(examId, task);
      submitText = "Dodaj Zadanie";
      break;
    case 'edit':
      action = (task) => console.log(task);
      submitText = "Edytuj Zadanie";
      break;
    default:
      action = (task) => {
        console.log("Form type not provided, performing default action:");
        console.log(task);
      }
      submitText = "Zatwierdź";
  }


  return (
    <form onSubmit={handleSubmit(action)} className={styles.lessonForm}>
      <BaseTaskFormModule control={control} errors={errors} setValue={setValue} />
      
      {module}

      <Box display="flex" justifyContent="flex-end">
        <Button color="primary" type="submit" variant="contained" startIcon={<CheckIcon />}>
          {submitText}
        </Button >
        {mode === "edit" &&
          <Button onClick={() => resetEdited()} color="secondary" variant="contained" startIcon={<CloseIcon />} style={{marginLeft: "30px"}}>
            Anuluj
          </Button >}
      </Box>
    </form>
  );
}


