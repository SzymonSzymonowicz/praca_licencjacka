import { Grid, Paper, TextField, Typography } from '@material-ui/core'
import React from 'react'

export default function FillBlanksTask(props) {
  const fill = props.fill.split("<blank>")

  const [blanksFilled, setBlanksFilled] = React.useState(new Array(fill.length-1).fill(""))

  const handleChange = (event, blankIndex) => {
    let items = [...blanksFilled];
    items[blankIndex] = event.target.value;
    setBlanksFilled(items)

    if(!props.answered.some(item => item['idExercise'] === props.id))
      props.setAnswered(props.answered.concat({idExercise: props.id, answer: event.target.value}))
    else
      props.setAnswered(props.answered.map(item => item['idExercise'] === props.id ? {idExercise: props.id, answer: items} : item))

  };

  const pointsString = props.loadValue === true ?  `(${props.answered[props.index]['gainedPoints']} / ${props.points} pkt.)` : `(${props.points} pkt.)`
  
  return (
    <Paper elevation={4} style={{padding: 20}}>
      <Typography>{`Zadanie. ${props.index + 1}   ${pointsString}`}</Typography>
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
            onChange={event => handleChange(event, index)} key={`t${props.id}b${index}`} 
            style={{margin: '0px 10px', minWidth: '60px'}} 
            inputProps={{ style: { textAlign: "center" } }}
            {...(props.loadValue === true  && {
              value: props.answered.find(item => item['idExercise'] === props.id)['answer'][index],
              disabled: true,
            })}
          />}
        </React.Fragment>
      })}
      </Grid>
    </Paper>
  )
}
