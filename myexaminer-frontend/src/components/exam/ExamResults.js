import { Button, Grid } from '@material-ui/core'
import ClosedTask from 'components/exam/ClosedTask'
import OpenTask from 'components/exam/OpenTask'
import FillBlanksTask from 'components/exam/FillBlanksTask'
import Comment from 'components/exam/Comment'
import React from 'react'
import { useParams, useHistory } from 'react-router-dom';
import { checkedExercisesForExamIdUrl, exercisesUrl } from 'router/urls';
import authHeader from 'services/auth-header';


export default function ExamResults(props) {
  let { id } = useParams();

  const [tasks, setTasks] = React.useState([]);
  const [answered, setAnswered] = React.useState([]);
  const history = useHistory();


  React.useEffect(() => {
    fetchAnswers()
    fetchTasks()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  async function fetchAnswers() {
    try {
      let answArr = []

      const result = await fetch(checkedExercisesForExamIdUrl + id, {
        method: 'GET',
        headers: authHeader()
      })
      const data = await result.json()

      data.forEach(result => {
        var answer = JSON.parse(result["answer"])["answerJSON"]

        answArr.push({ id: result["id"], answer: answer, gainedPoints: result["gainedPoints"], lecturerComment: result["lecturerComment"] })
      })

      // console.log(answArr)
      setAnswered(answArr)
    }
    catch(error) {
      console.log("Error fetching answers for exam.")
      console.log(error)
    }
  }

  async function fetchTasks() {
    try {
      const result = await fetch(exercisesUrl + id, {
        method: 'GET',
        headers: authHeader()
      })
      
      const data = await result.json()

      data.map(task => {
        task.content = JSON.parse(task.content)
        return task
      })

      // console.log(data)
      setTasks(data)
    }
    catch(error) {
      console.log("Error fetching answers for exam.")
      console.log(error)
    }
  }

  return (
    <Grid
    container
    direction={'column'} 
    justify="center"
    spacing={3}
    >
      
      {tasks.length && answered.length ? tasks.map((task, index) => {
        let type = task.content.type 
        let points = task.content.points 
        let instruction = task.content.instruction 
        let id = task.id
        let lecturerComment = answered[index]["lecturerComment"]

        if (type === "O") 
          return (
            <Grid item xs={12} key={index}>
              <OpenTask loadValue={true} answered={answered} setAnswered={setAnswered} id={id} instruction={instruction} points={points} index={index}/>
              {lecturerComment && <Comment lecturerComment={lecturerComment} disable={true}/>}
            </Grid>
          )
        else if (type === "Z")
          return (
            <Grid item xs={12} key={index}>
              <ClosedTask loadValue={true} answered={answered} setAnswered={setAnswered} id={id} instruction={instruction} points={points} answers={task.content.answers} index={index}/>
              {lecturerComment && <Comment lecturerComment={lecturerComment} disable={true}/>}
            </Grid>
          )
        else if (type === "L")
          return (
            <Grid item xs={12} key={index}>
              <FillBlanksTask loadValue={true} answered={answered} setAnswered={setAnswered} id={id} instruction={instruction} points={points} fill={task.content.fill} index={index}/>
              {lecturerComment && <Comment lecturerComment={lecturerComment} disable={true}/>}
            </Grid>
          )
        else
          return <></>
      })
    : ""}
      
      <Grid item style={{textAlign: "right", marginRight: "6%"}}><Button onClick={() => history.goBack()}>Powrót</Button></Grid>
    </Grid>
    )
}
