import { FormControl, FormControlLabel, Paper, Radio, RadioGroup, Typography } from '@material-ui/core'
import React from 'react'


export default function ClosedTask(props) {
  const [value, setValue] = React.useState(null);

  const handleChange = (event) => {
    setValue(event.target.value);
  };

  return (
    <Paper elevation={4} style={{padding: 20}}>
      <Typography>{`Zadanie. ${props.index + 1}   (${props.points} pkt.)`}</Typography>
      <Typography>{props.instruction}</Typography>
      <FormControl component="fieldset" fullWidth>
        <RadioGroup row name={`task${props.index}`} value={value} onChange={handleChange} style={{justifyContent: "space-evenly"}}>
          {props.answers.map(ans =>(
            <FormControlLabel key={ans} value={ans} control={<Radio />} label={String(ans).split(',')[0]} />
          ))}
        </RadioGroup>
    </FormControl>
    </Paper>
  )
}