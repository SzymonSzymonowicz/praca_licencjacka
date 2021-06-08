import { Grid, Paper } from "@material-ui/core";
import React, { useCallback, useEffect, useState } from "react";
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import TaskForm from "./task-form/TaskForm";
import TaskTypeEnum from "./task-form/TaskTypeEnum";
import OpenTask from "./OpenTask";
import ClosedTask from "./ClosedTask";
import FillBlanksTask from "./FillBlanksTask";
import { useParams } from "react-router";
import { examIdUrl } from "router/urls";
import authHeader from "services/auth-header";

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`full-width-tabpanel-${index}`}
      aria-labelledby={`full-width-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box p={3}>
          <Typography component="div">{children}</Typography>
        </Box>
      )}
    </div>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.any.isRequired,
  value: PropTypes.any.isRequired,
};

function a11yProps(index) {
  return {
    id: `full-width-tab-${index}`,
    'aria-controls': `full-width-tabpanel-${index}`,
  };
}

const useStyles = makeStyles((theme) => ({
  root: {
    backgroundColor: theme.palette.background.paper
  },
}));

export default function CreateExam(props) {
  const classes = useStyles();
  const { examId } = useParams();
  
  const [value, setValue] = useState(0);
  const [draftExam, setDraftExam] = useState(null);

  const tasks = draftExam?.exercises || [];

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  const getExam = useCallback(() => {
    fetch(examIdUrl(examId), {
      method: 'GET',
      headers: authHeader()
    }).then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        console.log(`Error during fetching exam of id ${examId}!`);
      }
    }).then( exam => {
      exam.exercises = exam?.exercises?.map(task => {
        task.content = JSON.parse(task.content);
        return task;
      })

      setDraftExam(exam);
    })
    .catch((error) => {
      console.log("Error fetching exam")
      console.log(error)
    })
  }, [examId])

  useEffect(() => {
    getExam();
  }, [getExam])
  
  return (
    <>
      <Grid container direction="column" justify="center" spacing={3}>
        <Grid item xs={12}>
          {tasks && <PreviewTasksModule tasks={tasks}/>}
          <Paper elevation={4} style={{ padding: 20, marginTop: 60}}>
            <div className={classes.root}>
              <AppBar position="static" color="default">
                <Tabs
                  value={value}
                  onChange={handleChange}
                  indicatorColor="primary"
                  textColor="primary"
                  variant="fullWidth"
                  aria-label="full width tabs"
                >
                  <Tab label="Zadanie otwarte" {...a11yProps(0)} />
                  <Tab label="Zadanie zamknięte" {...a11yProps(1)} />
                  <Tab label="Zadanie luki" {...a11yProps(2)} />
                </Tabs>
              </AppBar>
              <TabPanel value={value} index={0}>
                <TaskForm mode="create" type={TaskTypeEnum.OPEN} getExam={getExam} />
              </TabPanel>
              <TabPanel value={value} index={1}>
                <TaskForm mode="create" type={TaskTypeEnum.CLOSED} getExam={getExam} />
              </TabPanel>
              <TabPanel value={value} index={2}>
                <TaskForm mode="create" type={TaskTypeEnum.BLANKS} getExam={getExam} />
              </TabPanel>
            </div>
          </Paper>
        </Grid>
      </Grid>
    </>
  );
}

const PreviewTasksModule = ({ tasks }) => (
  <Grid
    container
    direction="column"
    justify="center"
    spacing={3}
  >
    {tasks && tasks?.map((task, index) => {
      let type = task.content.type 
      let points = task.content.points 
      let instruction = task.content.instruction 
      let id = task.id

      if (type === TaskTypeEnum.OPEN)
        return (
          <Grid item xs={12} key={`task${type}${index}`}>
            <OpenTask id={id} disabled instruction={instruction} points={points} index={index} />
          </Grid>
        )
      else if (type === TaskTypeEnum.CLOSED)
        return (
          <Grid item xs={12} key={`task${type}${index}`}>
            <ClosedTask id={id} disabled displayCorrect instruction={instruction} points={points} answers={task.content.answers} index={index} />
          </Grid>
        )
      else if (type === TaskTypeEnum.BLANKS)
        return (
          <Grid item xs={12} key={`task${type}${index}`}>
            <FillBlanksTask id={id} disabled instruction={instruction} points={points} fill={task.content.fill} index={index} />
          </Grid>
        )
      else
        return "";
    })}
  </Grid>
)