import { Accordion, AccordionDetails, AccordionSummary, Typography, makeStyles, AccordionActions, Button } from '@material-ui/core';
import React, { useEffect, useState } from 'react'
import ExpandMoreIcon from '@material-ui/icons/ExpandMore'
import EventIcon from '@material-ui/icons/Event';
import HourglassEmptyIcon from '@material-ui/icons/HourglassEmpty';
import { useHistory } from 'react-router-dom';
import { individualExamsForLecturerGroupsUrl } from 'router/urls';
import authHeader from 'services/auth-header';


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
    fetchAllIndividualExams();
  }, [])

  async function fetchAllIndividualExams() {
    try {
      const result = await fetch(individualExamsForLecturerGroupsUrl, {
        method: 'GET',
        headers: authHeader()
      })
      const data = await result.json()

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
        let date = new Date(exam.availableFrom);
        return (
          <Accordion expanded={expanded === `panel${index}`} onChange={handleChange(`panel${index}`)} key={index}>
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls="panel2bh-content"
              id="panel2bh-header"
            >
              <Typography className={classes.heading}>{exam.teachingGroupName}</Typography>
              <Typography className={classes.secondaryHeading}>{exam.name} student: {exam.studentIndex}</Typography>
            </AccordionSummary>
            <AccordionDetails>
              <Typography component="div">
                <div dangerouslySetInnerHTML={{__html: exam.description}}></div>
              </Typography>
            </AccordionDetails>
            
            <AccordionActions>
              <EventIcon/><Typography>{date.toLocaleString().split(',')[0]}</Typography>
              <HourglassEmptyIcon/><Typography style={{flexGrow: 1}}>{date.toLocaleString().split(',')[1]}</Typography>
              <Button size="small" onClick={() => {
                  history.push(`/landing/checkexam/${exam.individualExamId}`);
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
