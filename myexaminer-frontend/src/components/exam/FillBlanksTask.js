import { Grid, Paper, TextField, Typography } from '@material-ui/core'
import React from 'react'

export default function FillBlanksTask(props) {
  // probably needs refactoring .split(/(<blank>)/g)

  const fill = props.fill.split("<blank>")

  const [blanksFilled, setBlanksFilled] = React.useState(new Array(fill.length-1).fill(""))
  const [assignedPoints, setAssignedPoints] = React.useState(props.modify ? props.answered[props.index]['gainedPoints'] : null);

  const handleChange = (event, blankIndex) => {
    let items = [...blanksFilled];
    items[blankIndex] = event.target.value;
    setBlanksFilled(items)

    if(!props.answered.some(item => item['id'] === props.id))
      props.setAnswered(props.answered.concat({id: props.id, answer: event.target.value}))
    else
      props.setAnswered(props.answered.map(item => item['id'] === props.id ? {id: props.id, answer: items} : item))

  };

  const handlePointsChange = (event) => {
    setAssignedPoints(event.target.value);
    
    props.setAnswered(props.answered.map(item => item['id'] === props.id ? {...item, gainedPoints: event.target.value} : item))
  };

  const pointsString = props.loadValue === true ?  `(${props.answered[props.index]['gainedPoints']} / ${props.points} pkt.)` : `(${props.points} pkt.)`
  
  const pointsInput = <>
    ( <TextField inputProps={{style: { textAlign: 'center', width: 40, transform: 'translateY(-4px)' }}} value={assignedPoints} onChange={handlePointsChange}></TextField> {`/ ${props.points} pkt.)`} 
  </>

  return (
    <Paper elevation={4} style={{padding: 20}}>
      <Typography component={"div"}>{`Zadanie. ${props.index + 1}   `}{props.modify ? pointsInput : pointsString}</Typography>
      <Typography>{props.instruction}</Typography>
      <Grid
        container
        direction="row"
        justify="center"
        alignItems="center"
      >
      {fill.map( (str, index, {length}) => {

        return <React.Fragment key={`${props.id}seg${index}`}>
        <Typography key={str}>{str}</Typography>
        {index !== length - 1 && 
          <TextField 
            onInput={event => handleChange(event, index)} key={`t${props.id}b${index}`} 
            style={{margin: '0px 10px', minWidth: '60px'}} 
            inputProps={{ style: { textAlign: "center" } }}
            {...(props.loadValue === true  && {
              value: props.answered.find(item => item['id'] === props.id)['answer'][index],
              disabled: true,
            })}
            {...(props.disabled && {
              disabled: true,
            })}
          />}
        </React.Fragment>
      })}
      </Grid>
    </Paper>
  )
}
