import { Accordion, AccordionDetails, AccordionSummary, Typography, makeStyles, AccordionActions, Button } from '@material-ui/core';
import React from 'react'
import ExpandMoreIcon from '@material-ui/icons/ExpandMore'
import TimerIcon from '@material-ui/icons/Timer';
import EventIcon from '@material-ui/icons/Event';
import HourglassEmptyIcon from '@material-ui/icons/HourglassEmpty';
import { useHistory } from 'react-router-dom';
import { archiveExcercisesUrl } from 'router/urls';
import authHeader from 'services/auth-header';
import { getCurrentAccount } from 'services/auth-service';

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
  
  // fake state to remount component thus runing fetch on exams
  // const [update, setUpdate] = React.useState(false);

  const history = useHistory()

  const studentId = getCurrentAccount()?.id

  // const timeDiffNow = (date) => date.getTime() - Date.now();

  // // 30 secs
  // const lagMsDelay = 30000;
  // const times = exams?.map(exam => new Date(exam.examavailableFrom)).filter(date => timeDiffNow(date) > 0).sort((a, b) => a - b);

  // const soonestTime = times[0];
  // console.log(times);

  // React.useEffect(() => {
  //   if(soonestTime !== undefined) {
  //     setTimeout(() => {console.log("Odpalony o " + soonestTime)
  //       setUpdate(!update);
  //     }, timeDiffNow(soonestTime) + lagMsDelay);
  //   }
  //   // eslint-disable-next-line react-hooks/exhaustive-deps
  // }, []);

  const handleChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
  };

  function createAnswersForExam(studentId, examId){
    fetch(archiveExcercisesUrl, {
      method: 'POST',
      headers: {
        ...authHeader(),
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        studentId: studentId,
        examId: examId
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
        let date = new Date(exam.availableFrom);
        return (
          <Accordion expanded={expanded === `panel${index}`} onChange={handleChange(`panel${index}`)} key={index}>
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls="panel2bh-content"
              id="panel2bh-header"
            >
              <Typography className={classes.heading}>Egzamin {index+1}</Typography>
              <Typography className={classes.secondaryHeading}>
                {exam.name}
              </Typography>
            </AccordionSummary>
            <AccordionDetails>
              <Typography component="div">
                <div dangerouslySetInnerHTML={{__html: exam.description}}></div>
              </Typography>
            </AccordionDetails>
            
            <AccordionActions>
              <EventIcon/><Typography>{date.toLocaleString().split(',')[0]}</Typography>
              <HourglassEmptyIcon/><Typography>{date.toLocaleString().split(',')[1]}</Typography>
              <TimerIcon/><Typography style={{flexGrow: 1}}>{exam.duration} min.</Typography>
              <Button size="small" onClick={() => {
                  createAnswersForExam(studentId, exam.id);
                  history.push(`/landing/exam/${exam.id}`);
                }}
                {...(exam.state !== "OPEN" && {disabled: true})}    
              >
              Rozpocznij
              </Button>
              <Button size="small" color="primary"
                onClick={() => history.push(`/landing/examresults/${exam.id}`)}
                {...(exam.state !== "CHECKED" && {disabled: true})}
              >
                Wyniki
              </Button>
            </AccordionActions>
          </Accordion>
      )})}
    </>
  )
}
