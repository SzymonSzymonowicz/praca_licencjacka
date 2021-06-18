import React from "react";
import { useForm, Controller } from "react-hook-form";
import { Box, Button, TextField } from "@material-ui/core";
import styles from "components/group/group.module.css";
import { createChapterUrl, editChapterUrl } from "router/urls";
import authHeader from "services/auth-header";
import CheckIcon from '@material-ui/icons/Check';
import CloseIcon from '@material-ui/icons/Close';
import SunEditor from "suneditor-react";
import 'suneditor/dist/css/suneditor.min.css'; // Import Sun Editor's CSS File

const buttons = [
  ['undo', 'redo'],
  ['font', 'fontSize', 'formatBlock'],
  ['bold', 'underline', 'italic', 'strike', 'subscript', 'superscript'],
  ['fontColor', 'hiliteColor', 'textStyle'],
  ['removeFormat'],
  "/", // line break
  ['outdent', 'indent'],
  ['align', 'horizontalRule', 'list', 'lineHeight'],
  ['table', 'blockquote', 'link', 'image', 'video'],
  ['fullScreen', 'showBlocks', 'preview'] //, 'codeView'
];

const fonts = [
  "Arial",
  "Calibri",
  "Comic Sans",
  "Courier",
  "Garamond",
  "Georgia",
  "Impact",
  "Lucida Console",
  "Palatino Linotype",
  "Roboto",
  "Segoe UI",
  "Tahoma",
  "Times New Roman",
  "Trebuchet MS"
];

export default function ChapterForm(props) {
  const { chapter, lesson, type, resetEdited } = props;
  const lessonId = lesson?.id;

  const { control, formState: { errors }, reset, handleSubmit } = useForm({
    defaultValues: chapter || {
      title: "",
      content: ""
    }
  });

  const createChapter = (lessonId, chapter) => {
    fetch(createChapterUrl(lessonId), {
      method: "POST",
      headers: {
        ...authHeader(),
        "Content-Type": "application/json"
      },
      body: JSON.stringify(chapter)
    })
      .then(res => {
        if (res.status === 200) {
          reset();
          props?.getLesson(lessonId);
        }
        else {
          console.log("Adding lesson failed");
          console.log(res.text())
        }
    })
    .catch(err => { console.error(err) })
  }

  const editChapter = (chapterId, chapter) => {
    fetch(editChapterUrl(chapterId), {
      method: "PATCH",
      headers: {
        ...authHeader(),
        "Content-Type": "application/json"
      },
      body: JSON.stringify(chapter)
    })
      .then(res => {
        if (res.status === 200) {
          props?.getLesson(lessonId);
          
          if (typeof resetEdited === "function") {
            resetEdited();
          }
        }
        else {
          console.log("Editing chapter failed");
          console.log(res.text())
        }
    })
    .catch(err => { console.error(err) })
  }

  let action;
  let submitText;
  
  switch (type) {
    case 'create':
      action = (chapter) => createChapter(lessonId, chapter);
      submitText = "Utwórz Rozdział";
      break;
    case 'edit':
      action = (chapter) => editChapter(chapter?.id, chapter);
      submitText = "Edytuj Rozdział";
      break;
    default:
      action = (chapter) => {
        console.log("Form type not provided, performing default action:");
        console.log(chapter);
      }
      submitText = "Zatwierdź";
  }

  return (
    <form onSubmit={handleSubmit(action)} className={styles.chapterForm}>
      <Box className={styles.lessonInputBox}>
        <Controller
          control={control}
          name="title"
          render={({ field }) =>
            <TextField
              variant="outlined"
              label="Tytuł rozdziału"
              error={errors.title}
              fullWidth
              helperText={ errors.title ? errors.title?.message : null }
              {...field}
            />
          }
          rules={{
            required: "Wypełnij to pole",
          }}
        />
      
        <Controller
          control={control}
          name="content"
          render={({
            field: { value, ref, ...rest },
            // field: { onChange, onBlur, value, name, ref },
            fieldState: { invalid, isTouched, isDirty, error },
            formState
          }) => {
            return (
            <>
                <SunEditor
                setOptions={{
                  buttonList: buttons,
                  font: fonts,
                  imageFileInput: false
                }}
                lang="pl"
                placeholder="Uzupełnij treść rozdziału"
                setDefaultStyle="font-family: roboto; font-size: 16px;"
                defaultValue={value}
                {...rest}
              />
            </>)
          }}
          rules={{
            required: "Uzupełnij treść rozdziału",
            validate: value => value !== null || "Treść rozdziału nie może być pusta"
          }}
        />
        { errors.content && <span style={{color: "#f44336", marginLeft: "14px", top: "-20px", position: "relative"}} className="MuiFormHelperText-root">{errors.content.message}</span> }
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
