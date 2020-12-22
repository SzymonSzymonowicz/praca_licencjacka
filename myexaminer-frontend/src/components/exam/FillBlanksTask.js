import { Box, Container, Grid, Paper, TextField, Typography } from '@material-ui/core'
import React from 'react'

export default function FillBlanksTask(props) {
  const fill = props.fill

  return (
    <Paper elevation={4} style={{padding: 20}}>
      <Typography>{`Zadanie. ${props.index + 1}   (${props.points} pkt.)`}</Typography>
      <Typography>{props.instruction}</Typography>
      <Grid
        container
        direction="row"
        justify="center"
        alignItems="center"
      >
      {fill.split("<blank>").map( (str, index, {length}) => {
        
        return <React.Fragment key={`${props.index}seg${index}`}>
        <Typography key={str}>{str}</Typography>
        {index !== length - 1 && <TextField key={`t${props.index}b${index}`} style={{margin: '0px 10px', minWidth: '60px'}} inputProps={{ style: { textAlign: "center" } }}/>}
        </React.Fragment>
      })}
      </Grid>
    </Paper>
  )
}
