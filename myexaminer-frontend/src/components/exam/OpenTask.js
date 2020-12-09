import { Paper, TextField, Typography } from '@material-ui/core'
import React from 'react'

export default function OpenTask(props) {
  return (
    <Paper elevation={4} style={{padding: 20}}>
      <Typography>{`Zadanie. ${props.index + 1}   (${props.points} pkt.)`}</Typography>
      <Typography>{props.instruction}</Typography>
      <TextField
          id="outlined-multiline-static"
          label="Odpowiedź"
          multiline
          rows={4}
          variant="outlined"
          fullWidth
      />
    </Paper>
  )
}
