import React from "react";
import PropTypes from "prop-types";
import { makeStyles } from "@material-ui/core/styles";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";
import { useLocation } from "react-router-dom";

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
}));

export default function Lesson() {
  const classes = useStyles();
  const [value, setValue] = React.useState(0);

  const location = useLocation();
  const lesson = location.state;
  const chapters = lesson?.chapters;

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
        {chapters.map((chapter, index) => (
          <Tab
            key={`tab${index}`}
            label={chapter.title}
            {...a11yProps(index)}
          />
        ))}
      </Tabs>
      {chapters.map((chapter, index) => (
        <TabPanel value={value} index={index} key={`tab_panel${index}`}>
          <div dangerouslySetInnerHTML={{ __html: chapter.content }}></div>
        </TabPanel>
      ))}
    </div>
  );
}
