import { Button, IconButton } from '@material-ui/core';
import React from 'react';
import EditIcon from '@material-ui/icons/Edit';

export default function EditButton(props) {
  const { text, action, onlyIcon, ...other } = props;

  // onlyIcon
  const iconButton = (
    <IconButton aria-label="edit" {...other}>
      <EditIcon/>
    </IconButton >
  );

  const textButton = (
    <Button
      variant="contained"
      startIcon={<EditIcon />}
      {...other}
    >
      {text || "Edytuj"}
    </Button>
  );

  return (
    onlyIcon ? iconButton : textButton
  );
}
