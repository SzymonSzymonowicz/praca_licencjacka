import { Button, Grid } from '@material-ui/core'
import ClosedTask from 'components/exam/ClosedTask'
import OpenTask from 'components/exam/OpenTask'
import FillBlanksTask from 'components/exam/FillBlanksTask'
import React, { useEffect, useState } from 'react'
import { useHistory, useLocation, useParams } from 'react-router-dom';
import { archiveCheckUrl, exercisesUrl } from 'router/urls';
import { shuffle } from 'utils/shuffle';
import authHeader from 'services/auth-header';
import Timer from './Timer'
import { timeLeftInMinsAndSecs } from 'utils/dateUtils'


export default function Exam() {
  let { id } = useParams();
  const history = useHistory();
  const location = useLocation();
  const { exam } = location.state;

  const [tasks, setTasks] = useState([]);
  const [answered, setAnswered] = useState([]);

  useEffect(() => {
    fetch(exercisesUrl + id, {
      method: 'GET',
      headers: authHeader()
    }).then(function (response) {
      if (response.status === 200) {
        return response.json()
      } else {
        console.log("Something went wrong!")
      }
    }).then( tasksJson => {
      let answArr = []

      tasksJson.map(task => {
        answArr.push({id: task["id"], answer: null})
        task.content = JSON.parse(task.content)
        if(task.content.type === "Z")
          task.content.answers = shuffle(task.content.answers)
        return task
      })
      console.log(tasksJson)
      setTasks(tasksJson);

      console.log(answArr)
      setAnswered(answArr)
    })
    .catch(function (error) {
      console.log("Error fetching exam")
      console.log(error)
    })
  }, [id])

  function saveAnswers(receivedExercises, individualExamId, examId){
    fetch(archiveCheckUrl, {
      method: 'PUT',
      headers: {
        ...authHeader(),
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      body: JSON.stringify({
        receivedExercises: receivedExercises,
        individualExamId: individualExamId,
        examId: examId
      })
    }).then(function (response) {
      if (response.status === 200) {
        console.log("Exam saved properly!")
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

  const [minutesLeft, secondsLeft] = timeLeftInMinsAndSecs(exam?.availableFrom, exam?.duration);

  return (
    <form onSubmit={handleSubmit}>
      <Grid
      container
      direction="column"
      justify="center"
      spacing={3}
      >
        {tasks.map((task, index) => {
          let type = task.content.type 
          let points = task.content.points 
          let instruction = task.content.instruction 
          let id = task.id

          if (type === "O") 
            return (
              <Grid item xs={12} key={index}>
                <OpenTask answered={answered} setAnswered={setAnswered} id={id} instruction={instruction} points={points} index={index}/>
              </Grid>
            )
          else if (type === "Z")
            return (
              <Grid item xs={12} key={index}>
                <ClosedTask answered={answered} setAnswered={setAnswered} id={id} instruction={instruction} points={points} answers={task.content.answers} index={index}/>
              </Grid>
            )
          else if (type === "L")
            return (
              <Grid item xs={12} key={index}>
                <FillBlanksTask answered={answered} setAnswered={setAnswered} id={id} instruction={instruction} points={points} fill={task.content.fill} index={index}/>
              </Grid>
            )
          else
            return <></>
        })}
        
        <Grid item style={{ textAlign: "right", marginRight: "6%", display: "flex" }}>
          <Timer initialMinute={minutesLeft} initialSeconds={secondsLeft} secondsCap={exam?.duration * 60}/>
          <Button style={{flexGrow: 1, justifyContent: "flex-end"}} type="submit">Wyślij rozwiązania</Button>
        </Grid>
      </Grid>
    </form>
    )
}
