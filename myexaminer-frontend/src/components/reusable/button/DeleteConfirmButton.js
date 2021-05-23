import { Button, IconButton } from "@material-ui/core";
import React, { useEffect, useState } from "react";
import DeleteIcon from "@material-ui/icons/Delete";
import DeleteForeverIcon from "@material-ui/icons/DeleteForever";

// if you pass function with parameters it should be wrapped in anonymous function, so it does not excecute 
// ex.function(){ action(p1, p2) } or simply() => {... }
export default function DeleteConfirmButton(props) {
  const [clicked, setClicked] = useState(false);
  const { text, action, onlyIcon } = props;

  useEffect(() => {
    // turn off, if did not confirm after 5s
    const timer = () => setTimeout(() => {
      if (clicked) {
        setClicked(false);
      }
    }, props?.delay || 5000);

    // clear timeout if component unmounted
    const timerId = timer();
    return () => {
      clearTimeout(timerId);
    };
  });

  const handleClick = () => {
    if (clicked === true) {
      console.log(typeof action);
      typeof action === "function" ? action() : console.log("Given action is not a function! Performing mock log.");
    }

    setClicked(true);
  };

  // onlyIcon
  const iconButton = (
    <IconButton aria-label="delete" color="secondary" onClick={handleClick}>
      {clicked ? <DeleteForeverIcon /> : <DeleteIcon />}
    </IconButton >
  );

  const textButton = (
    <Button
      variant="contained"
      color="secondary"
      onClick={handleClick}
      startIcon={clicked ? <DeleteForeverIcon /> : <DeleteIcon />}
    >
      {clicked ? props?.confirmTitle || "Zatwierdź" : text}
    </Button>
  );

  return (
    onlyIcon ? iconButton : textButton
  );
}
