import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import { makeStyles } from "@material-ui/core/styles";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";
import { useLocation } from "react-router-dom";
import ChapterForm from "./ChapterForm";
import { lessonIdUrl } from "router/urls";
import authHeader from "services/auth-header";
import 'suneditor/dist/css/suneditor.min.css'; // Import Sun Editor's CSS File

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`vertical-tabpanel-${index}`}
      aria-labelledby={`vertical-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box p={1}>
          <Typography style={{height: "100%"}} component="div" className="sun-editor-editable">{children}</Typography>
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
    id: `vertical-tab-${index}`,
    "aria-controls": `vertical-tabpanel-${index}`,
  };
}

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    backgroundColor: theme.palette.background.paper,
    display: "flex",
    minHeight: "80%",
  },
  tabs: {
    borderRight: `1px solid ${theme.palette.divider}`,
    minWidth: "20%",
  },
  tabPanel: {
    flexGrow: 2
  }
}));

export default function Lesson() {
  const classes = useStyles();
  const [value, setValue] = useState(0);
  const [lesson, setLesson] = useState();

  const location = useLocation();
  const { lessonId } = location.state;
  const chapters = lesson?.chapters;

  const getLesson = (lessonId) => {
    fetch(lessonIdUrl(lessonId), {
      method: "GET",
      headers: authHeader()
    })
      .then(res => res.json())
      .then(lesson => {
        setLesson(lesson);
      })
      .catch(err => {
        console.log("Failed to fetch lesson [id: " + lessonId + "]");
        console.error(err);
      })
  }

  useEffect(() => {
    getLesson(lessonId)
  }, [])

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <div className={classes.root}>
      <Tabs
        orientation="vertical"
        variant="scrollable"
        value={value}
        onChange={handleChange}
        aria-label="Vertical tabs example"
        className={classes.tabs}
      >
        {chapters && chapters.map((chapter, index) => (
          <Tab
            key={`tab${index}`}
            label={chapter.title}
            {...a11yProps(index)}
          />
        ))}
          <Tab
            label="Dodaj rozdział"
            {...a11yProps(chapters?.length || 0)}
            style={{backgroundColor: "lightgray"}}
          />
      </Tabs>
      {chapters && chapters.map((chapter, index) => (
        <TabPanel value={value} index={index} key={`tab_panel${index}`} className={classes.tabPanel}>
          <div dangerouslySetInnerHTML={{ __html: chapter.content }}></div>
        </TabPanel>
      ))}
      <TabPanel value={value} index={chapters?.length || 0} style={{ flexGrow: 2 }}>
        <ChapterForm lesson={lesson} type="create" getLesson={ getLesson }/>
      </TabPanel>
    </div>
  );
}
