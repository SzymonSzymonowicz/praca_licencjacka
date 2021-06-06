import { Grid, Paper } from "@material-ui/core";
import React from "react";
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import TaskForm from "./task-form/TaskForm";

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
  const [value, setValue] = React.useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };
  
  
  return (
    <>
      <Grid container direction="column" justify="center" spacing={3}>
        <Grid item xs={12}>
          <Paper elevation={4} style={{ padding: 20 }}>
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
                  <Tab label="Zadanie otwrate" {...a11yProps(0)} />
                  <Tab label="Zadanie zamknięte" {...a11yProps(1)} />
                  <Tab label="Zadanie luki" {...a11yProps(2)} />
                </Tabs>
              </AppBar>
              <TabPanel value={value} index={0}>
                <TaskForm/>
              </TabPanel>
              <TabPanel value={value} index={1}>
                Formularz Zakmniete
              </TabPanel>
              <TabPanel value={value} index={2}>
                Formularz Luki
              </TabPanel>
            </div>
          </Paper>
        </Grid>
      </Grid>
    </>
  );
}