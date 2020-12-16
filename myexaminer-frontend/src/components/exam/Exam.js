import { Button, Grid } from '@material-ui/core'
import Closedtask from 'components/exam/ClosedTask'
import OpenTask from 'components/exam/OpenTask'
import React from 'react'
import { useParams } from 'react-router-dom';


//The Fisher-Yates (aka Knuth) Shuffle
function shuffle(array) {
  var currentIndex = array.length, temporaryValue, randomIndex;

  // While there remain elements to shuffle...
  while (0 !== currentIndex) {

    // Pick a remaining element...
    randomIndex = Math.floor(Math.random() * currentIndex);
    currentIndex -= 1;

    // And swap it with the current element.
    temporaryValue = array[currentIndex];
    array[currentIndex] = array[randomIndex];
    array[randomIndex] = temporaryValue;
  }

  return array;
}

export default function Exam(props) {
  // TODO set up fetching from backend
  // id for request from backend
  let { id } = useParams();

  const tasks = [
    {
      type: "O",
      points: 2,
      instruction: "Polecenie trudne nie do zrobienia",
    },
    {
      type: "O",
      points: 5,
      instruction: "Całka powierzchniowa z rogu gabriela",
    },
    {
      type: "Z",
      points: 1,
      instruction: "ABCD?",
      answers: ["Yep, T", "Nopers, F", "Lubie placki, F", "Heeheheerbata, F"]
    }
  ]

  return (
    <Grid
    container
    direction="column"
    justify="center"
    spacing={3}
    >
      {tasks.map((task,index) => {
        if (task.type === "O") 
          return <Grid item xs={12} key={index}><OpenTask index={index} instruction={task.instruction} points={task.points}/></Grid>
        else if (task.type === "Z")
          return <Grid item xs={12} key={index}><Closedtask index={index} instruction={task.instruction} points={task.points} answers={shuffle(task.answers)}/></Grid>
        else
          return <></>
      })}
      
      <Grid item style={{textAlign: "right", marginRight: "6%"}}><Button>Wyślij rozwiązania</Button></Grid>
    </Grid>
    )
}

