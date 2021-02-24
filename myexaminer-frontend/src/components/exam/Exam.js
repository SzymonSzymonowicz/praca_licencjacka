import { Button, Grid } from '@material-ui/core'
import ClosedTask from 'components/exam/ClosedTask'
import OpenTask from 'components/exam/OpenTask'
import FillBlanksTask from 'components/exam/FillBlanksTask'
import React from 'react'
import { useHistory, useParams } from 'react-router-dom';


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
  const history = useHistory();

  const [tasks, setTasks] = React.useState([]);
  const [answered, setAnswered] = React.useState([]);

/*  const idStudent = 2;*/

  React.useEffect(() => {
    fetch('http://localhost:8080/exercise/' + id, {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization':'Basic ' + window.btoa(sessionStorage.getItem('USER_SESSION_EMAIL') + ":" + sessionStorage.getItem('USER_SESSION_PASSWORD'))
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
        if(task.exerciseBody.type === "Z")
          task.exerciseBody.answers = shuffle(task.exerciseBody.answers)
        return task
      })
      // tasksJson.map(task => {
      //   if(task.type === "Z"){
      //     task.answers = shuffle(task.answers)
      //     console.log("elo")
      //     return task
      //   }else
      //     return task
      // })
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

  function saveAnswers(receivedExercises, idIndividualExam, idExam){
    fetch('http://localhost:8080/archive/checkExercises', {
      method: 'PUT',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization':'Basic ' + window.btoa(sessionStorage.getItem('USER_SESSION_EMAIL') + ":" + sessionStorage.getItem('USER_SESSION_PASSWORD'))
      },
      body: JSON.stringify({
        receivedExercises: receivedExercises,
        idIndividualExam: idIndividualExam,
        idExam: idExam
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

    saveAnswers(answered, null, id)
    history.goBack();
  }


  return (
    <form onSubmit={handleSubmit}>
      <Grid
      container
      direction="column"
      justify="center"
      spacing={3}
      >
        {tasks.map((task, index) => {
          let type = task.exerciseBody.type 
          let points = task.exerciseBody.points 
          let instruction = task.exerciseBody.instruction 
          let id = task.idExercise

          if (type === "O") 
            return (
              <Grid item xs={12} key={index}>
                <OpenTask answered={answered} setAnswered={setAnswered} id={id} instruction={instruction} points={points} index={index}/>
              </Grid>
            )
          else if (type === "Z")
            return (
              <Grid item xs={12} key={index}>
                <ClosedTask answered={answered} setAnswered={setAnswered} id={id} instruction={instruction} points={points} answers={task.exerciseBody.answers} index={index}/>
              </Grid>
            )
          else if (type === "L")
            return (
              <Grid item xs={12} key={index}>
                <FillBlanksTask answered={answered} setAnswered={setAnswered} id={id} instruction={instruction} points={points} fill={task.exerciseBody.fill} index={index}/>
              </Grid>
            )
          else
            return <></>
        })}
        
        <Grid item style={{textAlign: "right", marginRight: "6%"}}><Button type="submit">Wyślij rozwiązania</Button></Grid>
      </Grid>
    </form>
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