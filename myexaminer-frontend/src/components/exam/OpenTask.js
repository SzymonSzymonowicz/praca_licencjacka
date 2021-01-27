import { Paper, TextField, Typography } from '@material-ui/core'
import React from 'react'

export default function OpenTask(props) {
  const handleChange = (event) => {
    if(!props.answered.some(item => item['idExercise'] === props.id))
      props.setAnswered(props.answered.concat({idExercise: props.id, answer: event.target.value}))
    else
      props.setAnswered(props.answered.map(item => item['idExercise'] === props.id ? {idExercise: props.id, answer: event.target.value} : item))
  };
  // props.answered[props.id]['gainedPoints']
  const pointsString = props.loadValue === true ?  `(${props.answered[props.index]['gainedPoints']} / ${props.points} pkt.)` : `(${props.points} pkt.)`

  return (
    <Paper elevation={4} style={{padding: 20}}>
      <Typography>{`Zadanie. ${props.index + 1}   ${pointsString}`}</Typography>
      <Typography>{props.instruction}</Typography>
      <TextField
          id="outlined-multiline-static"
          label="Odpowiedź"
          multiline
          rows={4}
          variant="outlined"
          fullWidth
          onChange={handleChange}

          {...(props.loadValue === true && {
            value: props.answered.find(item => item['idExercise'] === props.id)['answer'],
            disabled: true,
            label: null,
          })}
      />
    </Paper>
  )
}
