import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import { makeStyles } from "@material-ui/core/styles";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";
import { useLocation } from "react-router-dom";
import ChapterForm from "./ChapterForm";
import { chapterIdUrl, lessonIdUrl } from "router/urls";
import authHeader from "services/auth-header";
// import 'suneditor/dist/css/suneditor.min.css'; // Import Sun Editor's CSS File
import { isLecturer } from "services/auth-service";
import { IconButton } from "@material-ui/core";
import DeleteConfirmButton from "components/reusable/button/DeleteConfirmButton";
import EditIcon from '@material-ui/icons/Edit';


function TabPanel(props) {
  const { children, value, view, index, ...other } = props;

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
          <Typography style={{ height: "100%" }} component="div" {...(view && {className: "sun-editor-editable"})}>{children}</Typography>
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
  const [editedChapter, setEditedChapter] = useState(null);

  const location = useLocation();
  const { lessonId } = location.state;
  const chapters = lesson?.chapters;

  const resetEdited = () => setEditedChapter(null);

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

  const deleteChapter = (lessonId, chapterId) => {
    fetch(chapterIdUrl(lessonId, chapterId), {
      method: "DELETE",
      headers: authHeader()
    })
      .then(res => {
        if (res.status === 200) {
          getLesson(lessonId);
        }
        return res.text();
      })
    .catch(err => {
      console.log("Failed to fetch lesson [id: " + lessonId + "]");
      console.error(err);
    })
  }

  useEffect(() => {
    getLesson(lessonId)
  }, [lessonId])

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
        {isLecturer() &&
          <Tab
            label="Dodaj rozdział"
            {...a11yProps(chapters?.length || 0)}
            style={{backgroundColor: "lightgray"}}
          />}
      </Tabs>
      {chapters && chapters.map((chapter, index) => (
        editedChapter === chapter?.id
        ?
        <TabPanel value={value} index={index} key={`tab_panel${index}`} style={{ flexGrow: 2 }}>
            <ChapterForm chapter={chapter} lesson={lesson} type="edit" getLesson={getLesson} resetEdited={resetEdited}/>
        </TabPanel>
        :
        <TabPanel view value={value} index={index} key={`tab_panel${index}`} className={classes.tabPanel}>
          {isLecturer() && 
            <Box textAlign="right">
              <IconButton onClick={() => setEditedChapter(chapter?.id)}>
                <EditIcon/>
              </IconButton>
              <DeleteConfirmButton onlyIcon action={ () => deleteChapter(lessonId, chapter?.id)}/>
            </Box>
          }
          <div dangerouslySetInnerHTML={{ __html: chapter.content }}/>
        </TabPanel>
      )
    )}
      {isLecturer() &&
        <TabPanel value={value} index={chapters?.length || 0} style={{ flexGrow: 2 }}>
          <ChapterForm lesson={lesson} type="create" getLesson={getLesson} />
        </TabPanel>}
    </div>
  );
}
