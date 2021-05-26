import React from "react";
import { useForm, Controller } from "react-hook-form";
import { Box, Button, TextField } from "@material-ui/core";
import styles from "components/group/group.module.css";
import { createLessonUrl, lessonIdUrl } from "router/urls";
import authHeader from "services/auth-header";
import CheckIcon from '@material-ui/icons/Check';
import CloseIcon from '@material-ui/icons/Close';

export default function LessonForm(props) {
  const { groupId, lesson, type, resetEdited } = props;
  const lessonId = lesson?.id;

  const { control, formState: { errors }, reset, handleSubmit } = useForm({
    defaultValues: lesson || {
      topic: "",
      description: "",
      date: ""
    }
  });

  const createLesson = (groupId, lesson) => {
    fetch(createLessonUrl(groupId), {
      method: "POST",
      headers: {
        ...authHeader(),
        "Content-Type": "application/json"
      },
      body: JSON.stringify(lesson)
    })
      .then(res => {
        if (res.status === 200) {
          reset();
          props?.getGroup(groupId);
        }
        else {
          console.log("Adding lesson failed");
          console.log(res.text())
        }
    })
    .catch(err => { console.error(err) })
  }

  const editLesson = (lessonId, lesson) => {
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
          props?.getGroup(groupId);
          
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
  
  switch (type) {
    case 'create':
      action = (lesson) => createLesson(groupId, lesson);
      submitText = "Utwórz Lekcję";
      break;
    case 'edit':
      action = (lesson) => editLesson(lessonId, lesson);
      submitText = "Edytuj Lekcję";
      break;
    default:
      action = (lesson) => {
        console.log("Form type not provided, performing default action:");
        console.log(lesson);
      }
      submitText = "Zatwierdź";
  }

  return (
    <form onSubmit={handleSubmit(action)} className={styles.lessonForm}>
      <Box style={{ minHeight: "80px" }} className={styles.lessonInputBox}>
        <Controller
          control={control}
          name="topic"
          render={({ field }) =>
            <TextField
              variant="outlined"
              label="Temat lekcji"
              error={errors.topic}
              fullWidth
              helperText={ errors.topic ? errors.topic?.message : null }
              {...field}
            />
          }
          rules={{
            required: "Wypełnij to pole",
          }}
        />
      
        <Controller
          control={control}
          name="description"
          render={({ field }) =>
            <TextField
              variant="outlined"
              label="Opis zajęć"
              error={errors.description}
              fullWidth
              multiline
              helperText={ errors.description ? errors.description?.message : null }
              {...field}
            />
          }
          rules={{
            required: "Wypełnij to pole",
          }}
        />

        <Controller
          control={control}
          name="date"
          render={({ field }) =>
            <TextField
              variant="outlined"
              label="Data zajęć"
              type="datetime-local"
              error={errors.date}
              fullWidth
              helperText={ errors.date ? errors.date?.message : null }
              InputLabelProps={{ shrink: true }}
              {...field}
            />
          }
          rules={{
            required: "Wypełnij to pole",
          }}
        />
      </Box>
      <Box display="flex" justifyContent="flex-end">
        <Button color="primary" type="submit" variant="contained" startIcon={<CheckIcon />}>
          {submitText}
        </Button >
        {type === "edit" &&
          <Button onClick={() => resetEdited()} color="secondary" variant="contained" startIcon={<CloseIcon />} style={{marginLeft: "30px"}}>
            Anuluj
          </Button >}
      </Box>
    </form>
  );
}
