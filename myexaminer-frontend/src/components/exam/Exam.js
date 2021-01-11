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

  const idStudent = 2;

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
      let answArr = []

      tasksJson.map(task => {
        answArr.push({idExercise: task["idExercise"], answer: null})
        task.exerciseBody = JSON.parse(task.exerciseBody)
        return task
      })
      tasksJson.map(task => {
        if(task.type === "Z"){
          shuffle(task.answers)
          return task
        }else
          return task
      })
      console.log(tasksJson)
      setTasks(tasksJson);

      //.map((_, i) => ({idExercise: i, answer: null}))
      console.log(answArr)
      setAnswered(answArr)
    })
    .catch(function (error) {
      console.log("Error fetching exam")
      console.log(error)
    })
  }, [id])

  function saveAnswers(idStudent, receivedExercises){
    fetch('http://localhost:8080/archive/checkExercises', {
      method: 'PUT',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        idStudent: idStudent,
        receivedExercises: receivedExercises
      })
    }).then(function (response) {
      if (response.status === 200) {
        console.log("Exam saved properly!")
        //history.push("/")
      } else {
        console.log("Something went wrong!")
      }
    }).catch(function (error) {
      console.log(error)
      console.log("error")
    })
  }

  const handleSubmit = event => {
    event.preventDefault();
    console.log(answered);

    saveAnswers(idStudent, answered)
  }


  return (
    <Grid
    container
    direction="column"
    justify="center"
    spacing={3}
    >
      <form onSubmit={handleSubmit}>
      {tasks.map((task, index) => {
        let type = task.exerciseBody.type 
        let points = task.exerciseBody.points 
        let instruction = task.exerciseBody.instruction 
        let id = task.idExercise

        if (type === "O") 
          return (
            <Grid item xs={12} key={index}>
              <OpenTask answered={answered} setAnswered={setAnswered} id={id} instruction={instruction} points={points} />
            </Grid>
          )
        else if (type === "Z")
          return (
            <Grid item xs={12} key={index}>
              <ClosedTask answered={answered} setAnswered={setAnswered} id={id} instruction={instruction} points={points} answers={task.exerciseBody.answers}/>
            </Grid>
          )
        else if (type === "L")
          return (
            <Grid item xs={12} key={index}>
              <FillBlanksTask answered={answered} setAnswered={setAnswered} id={id} instruction={instruction} points={points} fill={task.exerciseBody.fill}/>
            </Grid>
          )
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