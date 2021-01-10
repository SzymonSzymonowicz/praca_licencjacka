import { Button, Grid } from '@material-ui/core'
import ClosedTask from 'components/exam/ClosedTask'
import OpenTask from 'components/exam/OpenTask'
import FillBlanksTask from 'components/exam/FillBlanksTask'
import React from 'react'
import { useParams } from 'react-router-dom';


//The Fisher-Yates (aka Knuth) Shuffle
function shuffle(array) {
  var currentIndex = array.length, temporaryValue, randomIndex;

  while (0 !== currentIndex) {
    randomIndex = Math.floor(Math.random() * currentIndex);
    currentIndex -= 1;

    temporaryValue = array[currentIndex];
    array[currentIndex] = array[randomIndex];
    array[randomIndex] = temporaryValue;
  }

  return array;
}

export default function Exam() {
  let { id } = useParams();

  const [tasks, setTasks] = React.useState([]);
  const [answered, setAnswered] = React.useState([]);

  React.useEffect(() => {
    fetch('http://localhost:8080/exercise/' + id, {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(function (response) {
      if (response.status === 200) {
        return response.json()
      } else {
        console.log("Something went wrong!")
      }
    }).then( tasksJson => {
      //console.log(tasksJson.map(task => JSON.parse(task.exerciseBody)))
      setTasks(tasksJson.map(task => JSON.parse(task.exerciseBody))
                .map(task => {
                  if(task.type === "Z"){
                    shuffle(task.answers)
                    return task
                  }else
                    return task
              }));

      let answArr = new Array(tasksJson.length).fill().map((_, i) => ({taskId: i, answer: null}))
      //console.log(answArr)
      setAnswered(answArr)
    })
    .catch(function (error) {
      console.log("Error fetching exam")
      console.log(error)
    })
  }, [id])

  return (
    <Grid
    container
    direction="column"
    justify="center"
    spacing={3}
    >
      <form onSubmit={(event => {
        event.preventDefault();
        console.log(answered);
      })}>
      {tasks.map((task,index) => {
        if (task.type === "O") 
          return <Grid item xs={12} key={index}><OpenTask answered={answered} setAnswered={setAnswered} index={index} instruction={task.instruction} points={task.points} /></Grid>
        else if (task.type === "Z")
          return <Grid item xs={12} key={index}><ClosedTask answered={answered} setAnswered={setAnswered} index={index} instruction={task.instruction} points={task.points} answers={task.answers}/></Grid>
        else if (task.type === "L")
          return <Grid item xs={12} key={index}><FillBlanksTask answered={answered} setAnswered={setAnswered} index={index} instruction={task.instruction} points={task.points} fill={task.fill}/></Grid>
        else
          return <></>
      })}
      
      <Grid item style={{textAlign: "right", marginRight: "6%"}}><Button type="submit">Wyślij rozwiązania</Button></Grid>
      </form>
    </Grid>
    )
}

  // const fixedTasks = [
  //   {
  //     type: "O",
  //     points: 5,
  //     instruction: "Całka powierzchniowa z rogu gabriela",
  //   },
  //   {
  //     type: "Z",
  //     points: 1,
  //     instruction: "ABCD?",
  //     answers: ["Yep, T", "Nopers, F", "Lubie placki, F", "Heeheheerbata, F"]
  //   },
  //   {
  //     type: "L",
  //     points: 3,
  //     instruction: "Wpisz odpowiednie słowa",
  //     fill: "Welcome to the <blank>! <blank> in the jar. Sultans of <blank>."
  //   }
  // ]