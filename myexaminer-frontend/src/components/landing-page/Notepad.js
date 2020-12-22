import React, { useState, useEffect } from 'react';
import Fab from '@material-ui/core/Fab';
import {default as NoteIcon} from '@material-ui/icons/LibraryBooks';
import { useLocation} from 'react-router-dom';
import { Popover, TextField } from '@material-ui/core';

export default function Notepad({classes}, props) {
  const [notes, setNotes] = useState("");
  const [anchorEl, setAnchorEl] = useState(null);
  const [disabled, setDisabled] = useState(false);
  const location = useLocation()

  useEffect(() => {
    if(location.pathname.startsWith("/landing/exam/"))
      setDisabled(true)
    else
      setDisabled(false)
  }, [location])

  const handleFbaClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const openFab = Boolean(anchorEl);
  const id = openFab ? "simple-popover" : undefined;

  return(
    <>
      <Fab aria-label='Notepad' className={classes.fab} color='primary' onClick={handleFbaClick} disabled={disabled}>
        <NoteIcon/>
      </Fab>

      <Popover
        id={id}
        open={openFab}
        anchorEl={anchorEl}
        onClose={handleClose}
        anchorOrigin={{
          vertical: "top",
          horizontal: "left"
        }}
        transformOrigin={{
          vertical: "bottom",
          horizontal: "right"
        }}
        classes={{
          paper: classes.popPaper
        }}
      >
        <TextField
          multiline
          rows={12}
          //className={classes.typography}
          style={{padding: 20, width: "100%"}}
          value = {notes}
          onChange={(event) => {
            setNotes(event.target.value)
          }}
        />
      </Popover>
    </>
  )
}
