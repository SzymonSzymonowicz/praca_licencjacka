import React from "react";
import { useForm, Controller } from "react-hook-form";
import { Box, Button, TextField } from "@material-ui/core";
import styles from "components/group/group.module.css";
import { createLessonUrl } from "router/urls";
import authHeader from "services/auth-header";
import CheckIcon from '@material-ui/icons/Check';

export default function CreateLessonForm(props) {
  const { control, formState: { errors }, reset, handleSubmit } = useForm({
    defaultValues: {
      topic: "",
      description: "",
      date: ""
    }
  });
  const { groupId } = props;

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


  // <Typography gutterBottom variant="h5" component="h2">
  //   {lesson.topic}
  // </Typography>
  // <Typography
  //   variant="body2"
  //   color="textSecondary"
  //   component="p"
  // >
  //   {lesson.description}
  // </Typography>

  return (
    <form onSubmit={handleSubmit(lesson => createLesson(groupId, lesson))} className={styles.lessonForm}>
      <Box style={{ minHeight: "80px" }} className={styles.lessonInputBox}>
        <Controller
          control={control}
          name="topic"
          defaultValue=""
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
          defaultValue=""
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
          defaultValue=""
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
          Utwórz Lekcję
        </Button >
      </Box>
    </form>
  );
}
