import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";
import CalendarTodayIcon from "@material-ui/icons/CalendarToday";
import SchoolIcon from "@material-ui/icons/School";
import ForumIcon from "@material-ui/icons/Forum";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import { useHistory, useParams } from "react-router-dom";
import { groupByIdUrl } from "router/urls";
import authHeader from "services/auth-header";

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`wrapped-tabpanel-${index}`}
      aria-labelledby={`wrapped-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box p={5}>
          <Typography>{children}</Typography>
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
    id: `wrapped-tab-${index}`,
    "aria-controls": `wrapped-tabpanel-${index}`,
  };
}

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    backgroundColor: theme.palette.background.paper,
  },
}));

export default function Group(props) {
  const classes = useStyles();
  const [value, setValue] = useState(1);

  const history = useHistory();
  const { groupId } = useParams();

  const [group, setGroup] = useState();

  console.log("Wszedłeś na grupę: " + groupId);

  const getGroupById = (id) => {
    fetch(groupByIdUrl(id), {
      method: 'GET',
      headers: {
        ...authHeader(),
        "Content-Type": "application/json",
        'Accept': '*/*'
      }
    }).then(response => response.json()
    ).then(data => {
      console.log(data);
      setGroup(data);
    }).catch((error) => {
      console.log(error);
    });
  }

  useEffect(() => {
    getGroupById(groupId);
  }, []);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <div className={classes.root}>
      <AppBar position="static">
        <Tabs
          value={value}
          onChange={handleChange}
          aria-label="wrapped label tabs example"
        >
          <Tab
            icon={<SchoolIcon />}
            value={1}
            label="Zajęcia"
            wrapped
            {...a11yProps(1)}
          />
          <Tab
            icon={<CalendarTodayIcon />}
            value={2}
            label="Kalendarz"
            wrapped
            {...a11yProps(2)}
          />
          <Tab
            icon={<ForumIcon />}
            value={3}
            label="Forum"
            wrapped
            {...a11yProps(3)}
          />
        </Tabs>
      </AppBar>
      <TabPanel value={value} index={1}>
        {group && group?.lessons?.map((lesson, index) => (
          <Card className={classes.root} style={{ marginBottom: 30 }}>
            <CardActionArea>
              {/*                        <CardMedia
                                        component="img"
                                        alt="Contemplative Reptile"
                                        height="140"
                                        image="/static/images/cards/contemplative-reptile.jpg"
                                        title="Contemplative Reptile"
                                    />*/}
              <CardContent>
                <Typography gutterBottom variant="h5" component="h2">
                  {lesson.topic}
                </Typography>
                <Typography variant="body2" color="textSecondary" component="p">
                  {lesson.description}
                </Typography>
              </CardContent>
            </CardActionArea>
            <CardActions>
              <Button
                size="small"
                onClick={() => history.push(`/landing/lesson/`)}
              >
                Otwórz
              </Button>
            </CardActions>
          </Card>
        ))}
      </TabPanel>
      <TabPanel value={value} index={2}>
        Kalendarz
      </TabPanel>
      <TabPanel value={value} index={3}>
        Forum
      </TabPanel>
    </div>
  );
}
