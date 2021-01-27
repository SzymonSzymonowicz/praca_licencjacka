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

  const pointsString = props.loadValue === true ?  `(${props.answered[props.index]['gainedPoints']} / ${props.points} pkt.)` : `(${props.points} pkt.)`

  const markValue = props.loadValue === true ? props.answered.find(item => item['idExercise'] === props.id)['answer'] : value

  return (
    <Paper elevation={4} style={{padding: 20}}>
      <Typography>{`Zadanie. ${props.index + 1}   ${pointsString}`}</Typography>
      <Typography>{props.instruction}</Typography>
      <FormControl disabled={props.loadValue} component="fieldset" fullWidth>
        <RadioGroup row name={`task${props.id}`} value={markValue} onChange={handleChange} style={{justifyContent: "space-evenly"}}>
          {props.answers.map(ans =>(
            <FormControlLabel key={ans} value={ans} control={<Radio />} label={String(ans).split(',')[0]} />
          ))}
        </RadioGroup>
     </FormControl>
    </Paper>
  )
}
