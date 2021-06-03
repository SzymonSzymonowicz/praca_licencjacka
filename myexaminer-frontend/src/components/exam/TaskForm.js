import React, { useState } from 'react'
import { useForm, Controller, useFieldArray } from "react-hook-form";
import { Box, Button, TextField } from "@material-ui/core";
import styles from "components/group/group.module.css";
import { createLessonUrl, lessonIdUrl } from "router/urls";
import authHeader from "services/auth-header";
import CheckIcon from '@material-ui/icons/Check';
import CloseIcon from '@material-ui/icons/Close';
import Radio from '@material-ui/core/Radio';
import { isWholeNumber, isNumeric } from "utils/validationUtils";


export default function TaskForm(props) {
  const { groupId, type, task, taskType, resetEdited } = props;

  const { control, formState: { errors }, reset, handleSubmit, setValue, getValues } = useForm({
    defaultValues: task || {
      type: taskType,
      instruction: "",
      points: 1,
      // ??? fill: "hello <blank>!", answers:["1,F", "2,T", ...]
      answers: [
        { text: "", value: "T" },
        { text: "", value: "F" },
        { text: "", value: "F" },
        { text: "", value: "F" }
      ]
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
      action = (task) => console.log(task);
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

  const [radioValue, setRadioValue] = useState(0);

  const { fields } = useFieldArray(
    {
      control,
      name: "answers"
    }
  );

  const handleRadio = (selected) => {
    getValues()["answers"].forEach((_value, index) => {
      var field = `answers.${index}.value`;
      var newValue = selected === index ? "T" : "F";

      setValue(field, newValue);
    })
  }

  return (
    <form onSubmit={handleSubmit(action)} className={styles.lessonForm}>
      <Box style={{ minHeight: "80px" }} className={styles.lessonInputBox}>
        <Controller
          control={control}
          name="instruction"
          render={({ field }) =>
            <TextField
              variant="outlined"
              label="Polecenie"
              error={errors.instruction ? true : false}
              fullWidth
              helperText={ errors.instruction ? errors.instruction?.message : null }
              {...field}
            />
          }
          rules={{
            required: "Wypełnij to pole"
          }}
        />
      
        <Controller
          control={control}
          name="points"
          render={({ field: { onChange, ...rest } }) =>
            <TextField
              variant="outlined"
              label="Punkty"
              error={errors.points ? true : false}
              fullWidth
              type="number"
              helperText={errors.points ? errors.points?.message : null}
              onChange={(event) => {
                var num = event.target.value;
                onChange(num);
                setValue("points", parseInt(num), {shouldValidate: true})
              }}
              {...rest}
            />
          }
          rules={{
            required: "Wypełnij to pole",
            min: {
              value: 1,
              message: "Zadanie musi być warte przynajmniej jeden punkt"
            },
            validate: {
              numeric: v => isNumeric(v) || "Podana wartość musi być liczbą",
              wholeNumber: v => isWholeNumber(v) || "Punkty muszą być liczbą całkowitą"
            }
          }}
        />
        
        {fields.map((item, index) => {
          return (
            <Box key={item.id} display="flex" style={{paddingBottom: "20px", width: "50%"}}>
              <Controller
                control={control}
                name={`answers.${index}.value`}
                render={({ field: { onChange, onBlur, value, ...rest } }) =>
                  <Radio
                    value={index}
                    name={`answers.${index}.value`}
                    checked={index === radioValue}
                    onChange={(event) => {
                      setRadioValue(parseInt(event.target.value));
                      handleRadio(index);
                    }}
                    {...rest}
                  />
                }
              />
              
              <Controller
                control={control}
                name={`answers.${index}.text`}
                defaultValue={item.text}
                render={({ field }) =>
                  <TextField
                    variant="outlined"
                    label={`Odpowiedź nr ${index + 1}`}
                    fullWidth
                    error={errors?.answers?.[index]?.text ? true : false}
                    helperText={ errors?.answers?.[index]?.text ? errors?.answers[index]?.text?.message : null }
                    {...field}
                  />
                }
                rules={{
                  required: "Wypełnij to pole"
                }}
              />
            </Box>
          );
        })}
        
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