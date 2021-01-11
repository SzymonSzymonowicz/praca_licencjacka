import { Accordion, AccordionDetails, AccordionSummary, Typography, makeStyles, AccordionActions, Button } from '@material-ui/core';
import React from 'react'
import ExpandMoreIcon from '@material-ui/icons/ExpandMore'
import TimerIcon from '@material-ui/icons/Timer';
import EventIcon from '@material-ui/icons/Event';
import HourglassEmptyIcon from '@material-ui/icons/HourglassEmpty';
import { useHistory } from 'react-router-dom';

const useStyles = makeStyles((theme) => ({
  heading: {
    fontSize: theme.typography.pxToRem(15),
    flexBasis: '33.33%',
    flexShrink: 0,
  },
  secondaryHeading: {
    fontSize: theme.typography.pxToRem(15),
    color: theme.palette.text.secondary,
  },
}));

export default function Exams({exams}, props) {
  const classes = useStyles();
  const [expanded, setExpanded] = React.useState(false);

  const history = useHistory()

  const idStudent = 2

  const handleChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
  };

  //"archive/createExerciseArchive" POST

  function createAnswersForExam(idStudent, idExam){
    fetch('http://localhost:8080/archive/createExerciseArchive', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        idStudent: idStudent,
        idExam: idExam
      })
    }).then(function (response) {
      if (response.status === 200) {
        console.log("Exam created properly!")
        //history.push("/")
      } else {
        console.log("Something went wrong!")
      }
    }).catch(function (error) {
      console.log(error)
      console.log("error")
    })
  }


  return (
    <>
      {exams.map((exam,index) => (
      <Accordion expanded={expanded === `panel${index}`} onChange={handleChange(`panel${index}`)} key={index}>
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls="panel2bh-content"
          id="panel2bh-header"
        >
          <Typography className={classes.heading}>Egzamin {index+1}</Typography>
          <Typography className={classes.secondaryHeading}>
            {exam.title}
          </Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Typography component="div">
            <div dangerouslySetInnerHTML={{__html: exam.description}}></div>
          </Typography>
        </AccordionDetails>

        <AccordionActions>
          <EventIcon/><Typography>{exam.date}</Typography>
          <HourglassEmptyIcon/><Typography>{exam.hour}</Typography>
          <TimerIcon/><Typography style={{flexGrow: 1}}>{exam.duration}</Typography>
          <Button size="small" onClick={() => {
                createAnswersForExam(idStudent, exam.idExam);
                history.push(`/landing/exam/${exam.idExam}`);
              }}>
          Rozpocznij
          </Button>
          <Button size="small" color="primary">
            Wyniki
          </Button>
        </AccordionActions>
      </Accordion>))}
    </>
  )
}

// const fixedExams = [
//   {
//     "id": 0,
//     "title": "Idiomy",
//     "description": "Słówka z rodziału VI<br/> Present perfect, past simple, past perfect",
//     "date": "11-12-2020",
//     "hour": "13:30",
//     "duration": "45 min"
//   },
//   {
//     "id": 1,
//     "title": "Idiomy",
//     "description": "Słówka z rodziału VI<br/>Czasy<ul><li>Present perfect</li><li>past simple</li><li>past perfect</li></ul>",
//     "date": "11-12-2020",
//     "hour": "13:30",
//     "duration": "45 min"
//   }
// ]