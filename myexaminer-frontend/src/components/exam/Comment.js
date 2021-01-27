import { Typography, TextField, makeStyles } from '@material-ui/core'
import React from 'react'


const useStyles = makeStyles({
  textField: {
    background: '#DCDCDC',
  },
  input: {
    color: 'red',
    fontWeight: 'bold'
  }
});


function Comment({lecturerComment}) {
  const classes = useStyles();

  return (
    <>
      <Typography className={classes.input} variant="h6">Komentarz</Typography>
      <TextField
          className={classes.textField}
          inputProps={{className: classes.input}}
          id="outlined-multiline-static"
          multiline
          rows={2}
          variant="filled"
          fullWidth
          disabled
          // InputProps={{
          //   className: classes.multilineColor
          // }}
          color="primary"
          value={lecturerComment === null ? "" : lecturerComment}
      />
    </>
  )
}

export default Comment;
// Comment.propTypes = {
//   classes: PropTypes.object.isRequired,
//   props: PropTypes.any,
//   lecturerComment: PropTypes.string
// };

// export default withStyles(styles)(Comment);