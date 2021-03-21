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

  //"archive/exercises" POST

  function createAnswersForExam(idStudent, idExam){
    fetch('http://localhost:8080/archive/exercises', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization':'Basic ' + window.btoa(sessionStorage.getItem('USER_SESSION_EMAIL') + ":" + sessionStorage.getItem('USER_SESSION_PASSWORD'))
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
      {exams.map((exam,index) => {
        let date = new Date(exam.examAvailableDate);
        return (
          <Accordion expanded={expanded === `panel${index}`} onChange={handleChange(`panel${index}`)} key={index}>
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls="panel2bh-content"
              id="panel2bh-header"
            >
              <Typography className={classes.heading}>Egzamin {index+1}</Typography>
              <Typography className={classes.secondaryHeading}>
                {exam.examName}
              </Typography>
            </AccordionSummary>
            <AccordionDetails>
              <Typography component="div">
                <div dangerouslySetInnerHTML={{__html: exam.examDescription}}></div>
              </Typography>
            </AccordionDetails>
            
            <AccordionActions>
              <EventIcon/><Typography>{date.toLocaleString().split(',')[0]}</Typography>
              <HourglassEmptyIcon/><Typography>{date.toLocaleString().split(',')[1]}</Typography>
              <TimerIcon/><Typography style={{flexGrow: 1}}>{exam.examDurationTime} min.</Typography>
              <Button size="small" onClick={() => {
                  createAnswersForExam(idStudent, exam.idExam);
                  history.push(`/landing/exam/${exam.idExam}`);
                }}
                {...(exam.examStatus !== "OPEN" && {disabled: true})}    
              >
              Rozpocznij
              </Button>
              <Button size="small" color="primary"
                onClick={() => history.push(`/landing/examresults/${exam.idExam}`)}
                {...(exam.examStatus !== "CHECKED" && {disabled: true})}
              >
                Wyniki
              </Button>
            </AccordionActions>
          </Accordion>
      )})}
    </>
  )
}
