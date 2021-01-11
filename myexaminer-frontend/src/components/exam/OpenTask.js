import { Paper, TextField, Typography } from '@material-ui/core'
import React from 'react'

export default function OpenTask(props) {
  const handleChange = (event) => {
    if(!props.answered.some(item => item['idExercise'] === props.id))
      props.setAnswered(props.answered.concat({idExercise: props.id, answer: event.target.value}))
    else
      props.setAnswered(props.answered.map(item => item['idExercise'] === props.id ? {idExercise: props.id, answer: event.target.value} : item))
  };
  
  return (
    <Paper elevation={4} style={{padding: 20}}>
      <Typography>{`Zadanie. ${props.id + 1}   (${props.points} pkt.)`}</Typography>
      <Typography>{props.instruction}</Typography>
      <TextField
          id="outlined-multiline-static"
          label="Odpowiedź"
          multiline
          rows={4}
          variant="outlined"
          fullWidth
          onChange={handleChange}
      />
    </Paper>
  )
}
