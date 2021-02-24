import { Accordion, AccordionDetails, AccordionSummary, Typography, makeStyles, AccordionActions, Button } from '@material-ui/core';
import React, { useEffect, useState } from 'react'
import ExpandMoreIcon from '@material-ui/icons/ExpandMore'
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

export default function ExamsToCheck(props) {
  const classes = useStyles();
  const [expanded, setExpanded] = useState(false);
  const [individualExams, setIndividualExams] = useState([]);

  const history = useHistory()

  const handleChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
  };

  useEffect(() => {
    fetchAllIndividualExams()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  async function fetchAllIndividualExams() {
    try {
      const result = await fetch('http://localhost:8080/individualExam/examsToCheck', {
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': 'Basic ' + window.btoa(sessionStorage.getItem('USER_SESSION_EMAIL') + ":" + sessionStorage.getItem('USER_SESSION_PASSWORD'))
        }
      })
      const data = await result.json()

      console.log(data)
      setIndividualExams(data)
    }
    catch(error) {
      console.log("Error fetching individual exams to check.")
      console.log(error)
    }
  }


  return (
    <>
      {individualExams.length !== 0 && individualExams.map((exam,index) => {
        let date = new Date(exam.examAvailableDate);
        return (
          <Accordion expanded={expanded === `panel${index}`} onChange={handleChange(`panel${index}`)} key={index}>
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls="panel2bh-content"
              id="panel2bh-header"
            >
              <Typography className={classes.heading}>{exam.nameTeachingGroup}</Typography>
              <Typography className={classes.secondaryHeading}>{exam.examName} student: {exam.studentIndex}</Typography>
            </AccordionSummary>
            <AccordionDetails>
              <Typography component="div">
                <div dangerouslySetInnerHTML={{__html: exam.examDescription}}></div>
              </Typography>
            </AccordionDetails>
            
            <AccordionActions>
              <EventIcon/><Typography>{date.toLocaleString().split(',')[0]}</Typography>
              <HourglassEmptyIcon/><Typography style={{flexGrow: 1}}>{date.toLocaleString().split(',')[1]}</Typography>
              <Button size="small" onClick={() => {
                  history.push(`/landing/checkexam/${exam.idIndividualExam}`);
                }}   
              >
              Sprawdź
              </Button>
            </AccordionActions>
          </Accordion>
      )})}
    </>
  )
}

/*
const dummyData = [
  {
      "idIndividualExam": 3,
      "examName": "Łatwy sprawdzian",
      "examDescription": "Bardzo łatwy sprawdzian, prosze się nauczyć łatwych rzeczy",
      "examAvailableDate": "2021-01-20T12:15:00.000+00:00",
      "idTeachingGroup": 1,
      "nameTeachingGroup": "Grupa DT2 angielski",
      "studentIndex": "250558"
  },
  {
      "idIndividualExam": 4,
      "examName": "Łatwy sprawdzian",
      "examDescription": "Bardzo łatwy sprawdzian, prosze się nauczyć łatwych rzeczy",
      "examAvailableDate": "2021-01-20T12:15:00.000+00:00",
      "idTeachingGroup": 1,
      "nameTeachingGroup": "Grupa DT2 angielski",
      "studentIndex": "250123"
  },
  {
      "idIndividualExam": 1,
      "examName": "Trudny sprawdzian",
      "examDescription": "Bardzo trudny sprawdzian, prosze się nauczyć trudnych rzeczy",
      "examAvailableDate": "2020-12-24T10:10:10.000+00:00",
      "idTeachingGroup": 1,
      "nameTeachingGroup": "Grupa DT2 angielski",
      "studentIndex": "250558"
  },
  {
      "idIndividualExam": 2,
      "examName": "Trudny sprawdzian",
      "examDescription": "Bardzo trudny sprawdzian, prosze się nauczyć trudnych rzeczy",
      "examAvailableDate": "2020-12-24T10:10:10.000+00:00",
      "idTeachingGroup": 1,
      "nameTeachingGroup": "Grupa DT2 angielski",
      "studentIndex": "250123"
  }
]
*/