import { Button, Grid } from '@material-ui/core'
import ClosedTask from 'components/exam/ClosedTask'
import OpenTask from 'components/exam/OpenTask'
import FillBlanksTask from 'components/exam/FillBlanksTask'
import Comment from 'components/exam/Comment'
import React from 'react'
import { useParams, useHistory } from 'react-router-dom';
import { individualExamExercisesUrl, exercisesUrl, archiveCheckUrl } from 'router/urls';
import authHeader from 'services/auth-header';


export default function CheckExam(props) {
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

      const result = await fetch(individualExamExercisesUrl + id, {
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

  async function saveAnswers() {
    try {
      const result = await fetch(archiveCheckUrl, {
        method: 'PUT',
        headers: authHeader(),
        body: JSON.stringify({
          individualExamId: id,
          receivedExercises: answered
        })
      })
      
      const status = await result.status

      if(status === 200)
        console.log("Answers to individual exam overriden with new comments properly!")

      //history.push("/")
    }
    catch(error) {
      console.log("Error saving answers with new comments.")
      console.log(error)
    }
  }

  const handleSubmit = event => {
    event.preventDefault();
    console.log(answered);

    saveAnswers();
    history.goBack();
  }

  return (
    <form onSubmit={handleSubmit}>
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
                <OpenTask modify={true} loadValue={true} answered={answered} setAnswered={setAnswered} id={id} instruction={instruction} points={points} index={index}/>
                <Comment lecturerComment={lecturerComment} answered={answered} setAnswered={setAnswered} id={id}/>
              </Grid>
            )
          else if (type === "Z")
            return (
              <Grid item xs={12} key={index}>
                <ClosedTask modify={true} loadValue={true} answered={answered} setAnswered={setAnswered} id={id} instruction={instruction} points={points} answers={task.content.answers} index={index}/>
                <Comment lecturerComment={lecturerComment} answered={answered} setAnswered={setAnswered} id={id}/>
              </Grid>
            )
          else if (type === "L")
            return (
              <Grid item xs={12} key={index}>
                <FillBlanksTask modify={true} loadValue={true} answered={answered} setAnswered={setAnswered} id={id} instruction={instruction} points={points} fill={task.content.fill} index={index}/>
                <Comment lecturerComment={lecturerComment} answered={answered} setAnswered={setAnswered} id={id}/>
              </Grid>
            )
          else
            return <></>
        })
      : ""}
        
        <Grid item style={{textAlign: "right", marginRight: "6%", color: "red"}}><Button type="submit">Zakończ sprawdzanie</Button></Grid>
      </Grid>
    </form>
    )
}
