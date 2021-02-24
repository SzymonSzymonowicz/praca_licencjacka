import { Typography, TextField, makeStyles } from '@material-ui/core'
import React, { useState } from 'react'


const useStyles = makeStyles({
  textField: {
    background: '#DCDCDC',
  },
  input: {
    color: 'red',
    fontWeight: 'bold'
  }
});


function Comment(props, {lecturerComment, disable}) {
  const classes = useStyles();

  const [comment, setComment] = useState(props.lecturerComment);

  const handleChange = (event) => {
    setComment(event.target.value);

    props.setAnswered(props.answered.map(item => item['idExercise'] === props.id ? {...item, lecturerComment: event.target.value} : item))
  };
  
  return (
    <>
      <Typography className={classes.input} variant="h6">Komentarz</Typography>
      <TextField
          className={classes.textField}
          inputProps={{className: classes.input}}
          id="outlined-multiline-static"
          multiline
          rows={2}
          variant="filled"
          fullWidth
          disabled={props.disable}
          onChange={handleChange}
          color="primary"
          value={comment === null ? "" : comment}
      />
    </>
  )
}

export default Comment;
