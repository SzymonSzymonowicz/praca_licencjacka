import { Button } from "@material-ui/core";
import React, { useEffect, useState } from "react";
import DeleteIcon from "@material-ui/icons/Delete";
import DeleteForeverIcon from "@material-ui/icons/DeleteForever";

// if you pass function with parameters it should be wrapped in anonymous function, so it does not excecute 
// ex.function(){ action(p1, p2) } or simply() => {... }
export default function DeleteConfirmButton({ text, action }, props) {
  const [clicked, setClicked] = useState(false);

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
    console.log("Clicked :" + clicked);
    console.log(action);
    if (clicked === true) {
      console.log(typeof action);
      typeof action === "function" ? action() : console.log("Perform Action");
    }

    setClicked(true);
  };

  return (
    <>
      <Button
        variant="contained"
        color="secondary"
        onClick={handleClick}
        startIcon={clicked ? <DeleteForeverIcon /> : <DeleteIcon />}
      >
        {clicked ? props?.confirmTitle || "Zatwierdź" : text}
      </Button>
    </>
  );
}
