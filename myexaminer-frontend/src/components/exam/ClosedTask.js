import { FormControl, FormControlLabel, Paper, Radio, RadioGroup, TextField, Typography } from '@material-ui/core'
import React from 'react'


export default function ClosedTask(props) {
  const [value, setValue] = React.useState(null);

  const [assignedPoints, setAssignedPoints] = React.useState(props.modify ? props.answered[props.index]['gainedPoints'] : null);

  const handleChange = (event) => {
    setValue(event.target.value);
    if(!props.answered.some(item => item['id'] === props.id))
      props.setAnswered(props.answered.concat({id: props.id, answer: event.target.value}))
    else
      props.setAnswered(props.answered.map(item => item['id'] === props.id ? {id: props.id, answer: event.target.value} : item))
  };

  const handlePointsChange = (event) => {
    setAssignedPoints(event.target.value);
    
    props.setAnswered(props.answered.map(item => item['id'] === props.id ? {...item, gainedPoints: event.target.value} : item))
  };

  const pointsString = props.loadValue === true ?  `(${props.answered[props.index]['gainedPoints']} / ${props.points} pkt.)` : `(${props.points} pkt.)`
  
  const pointsInput = <>
    ( <TextField inputProps={{style: { textAlign: 'center', width: 40, transform: 'translateY(-4px)' }}} value={assignedPoints} onChange={handlePointsChange}></TextField> {`/ ${props.points} pkt.)`} 
  </>

  const markValue = props.loadValue === true ? props.answered.find(item => item['id'] === props.id)['answer'] : value

  return (
    <Paper elevation={4} style={{padding: 20}}>
      <Typography component={"div"}>{`Zadanie. ${props.index + 1}   `}{props.modify ? pointsInput : pointsString}</Typography>
      <Typography>{props.instruction}</Typography>
      <FormControl disabled={props.loadValue} component="fieldset" fullWidth {...(props.disabled && { disabled: true})}>
        <RadioGroup row name={`task${props.id}`} value={markValue} onChange={handleChange} style={{justifyContent: "space-evenly"}}>
          {props.answers.map(ans =>(
            <FormControlLabel key={ans} value={ans} control={<Radio />} label={String(ans).split(',')[0]} />
          ))}
        </RadioGroup>
     </FormControl>
    </Paper>
  )
}
