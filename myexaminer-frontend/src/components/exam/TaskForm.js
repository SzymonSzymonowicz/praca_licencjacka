import React, { useState } from 'react'
import { useForm, Controller, useFieldArray } from "react-hook-form";
import { Box, Button, TextField } from "@material-ui/core";
import styles from "components/group/group.module.css";
import { createLessonUrl, lessonIdUrl } from "router/urls";
import authHeader from "services/auth-header";
import CheckIcon from '@material-ui/icons/Check';
import CloseIcon from '@material-ui/icons/Close';

import { makeStyles } from '@material-ui/core/styles';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';
import FormHelperText from '@material-ui/core/FormHelperText';
import FormLabel from '@material-ui/core/FormLabel';

const isNumeric = (num) => {
  return !isNaN(num);
}

const isWholeNumber = (num) => {
  return Number.isInteger(num);
}

const useStyles = makeStyles((theme) => ({
  formControl: {
    margin: theme.spacing(3),
  },
  button: {
    margin: theme.spacing(1, 1, 0, 0),
  },
}));


export default function TaskForm(props) {
  const { groupId, type, task, taskType, resetEdited } = props;

  const { control, formState: { errors }, reset, handleSubmit } = useForm({
    defaultValues: task || {
      type: taskType,
      instruction: "",
      points: 1,
      // ??? fill: "hello <blank>!", answers:["1,F", "2,T", ...]
      answers: [
        { text: "", value: "F" },
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

// ==================================================
const classes = useStyles();
  const [value, setValue] = useState('');
  const [error, setError] = useState(false);
  const [helperText, setHelperText] = useState('Choose wisely');

  const handleRadioChange = (event) => {
    setValue(event.target.value);
    setHelperText(' ');
    setError(false);
  };

  const handleSubmit2 = (event) => {
    event.preventDefault();

    if (value === 'best') {
      setHelperText('You got it!');
      setError(false);
    } else if (value === 'worst') {
      setHelperText('Sorry, wrong answer!');
      setError(true);
    } else {
      setHelperText('Please select an option.');
      setError(true);
    }
  };
// ==================================================
  const { fields, append, prepend, remove, swap, move, insert } = useFieldArray(
    {
      control,
      name: "answers"
    }
  );
  // ==================================================

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
              error={errors.instruction}
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
          render={({ field }) =>
            <TextField
              variant="outlined"
              label="Punkty"
              error={errors.points}
              fullWidth
              type="number"
              helperText={ errors.points ? errors.points?.message : null }
              {...field}
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
        {/* ======= */}

        
        {/* ======= */}
        <ul>
        <FormControl component="fieldset" error={error} className={classes.formControl}>
            <RadioGroup aria-label="quiz" name="quiz" value={value} onChange={handleRadioChange}>
        {fields.map((item, index) => {
          return (
            <li key={item.id}>
              {/* <FormControlLabel name={`answers.${index}.value`} value="T" control={<Radio />} label={ */}
              <Controller
                control={control}
                name={`answers.${index}.value`}
                render={({ field }) =>
                  <Radio
                    {...field}
                    value="T"
                    name={`answers.${index}.value`}
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
                      label={`Odpowiedź nr ${index+1}`}
                      // error={errors.answers.index.text}
                      fullWidth
                      // helperText={ errors.answers.index.text ? errors.answer.index.text?.message : null }
                      {...field}
                    />
                  }
                  rules={{
                    required: "Wypełnij to pole"
                  }}
                // />
              // } />
              />

              {/* <input
                defaultValue={`${item.firstName}`} // make sure to set up defaultValue
                {...register(`test.${index}.firstName`)}
              />

              <Controller
                render={({ field }) => <input {...field} />}
                name={`answer.${index}.text`}
                control={control}
                defaultValue={item.text} // make sure to set up defaultValue
              /> */}
            </li>
          );
        })}
            </RadioGroup>
            <FormHelperText>{helperText}</FormHelperText>
            <Button type="submit" variant="outlined" color="primary" className={classes.button}>
              Check Answer
            </Button>
            </FormControl>
      </ul>
        
        
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