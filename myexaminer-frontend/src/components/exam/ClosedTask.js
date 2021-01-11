import { FormControl, FormControlLabel, Paper, Radio, RadioGroup, Typography } from '@material-ui/core'
import React from 'react'


export default function ClosedTask(props) {
  const [value, setValue] = React.useState(null);

  const handleChange = (event) => {
    setValue(event.target.value);
    if(!props.answered.some(item => item['idExercise'] === props.id))
      props.setAnswered(props.answered.concat({idExercise: props.id, answer: event.target.value}))
    else
      props.setAnswered(props.answered.map(item => item['idExercise'] === props.id ? {idExercise: props.id, answer: event.target.value} : item))
  };

  return (
    <Paper elevation={4} style={{padding: 20}}>
      <Typography>{`Zadanie. ${props.id + 1}   (${props.points} pkt.)`}</Typography>
      <Typography>{props.instruction}</Typography>
      <FormControl component="fieldset" fullWidth>
        <RadioGroup row name={`task${props.id}`} value={value} onChange={handleChange} style={{justifyContent: "space-evenly"}}>
          {props.answers.map(ans =>(
            <FormControlLabel key={ans} value={ans} control={<Radio />} label={String(ans).split(',')[0]} />
          ))}
        </RadioGroup>
     </FormControl>
    </Paper>
  )
}
