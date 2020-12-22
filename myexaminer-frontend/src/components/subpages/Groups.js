import React from "react";
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import CalendarTodayIcon from '@material-ui/icons/CalendarToday';
import SchoolIcon from '@material-ui/icons/School';
import ForumIcon from '@material-ui/icons/Forum';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import {useHistory} from "react-router-dom";

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
        'aria-controls': `wrapped-tabpanel-${index}`,
    };
}

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.paper,
    },
}));

export default function Groups(props) {
    const classes = useStyles();
    const [value, setValue] = React.useState('one');

    const history = useHistory()

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const lessons = [
        {
            "title": "Czasy przeszłe powtórzenie",
            "description": "past simple, past perfect",
        },
        {
            "title": "Czasy przyszłe powtórzenie",
            "description": "future simple, future continuous, future perfect",
        },
        {
            "title": "Czasy teraźniejsze powtórzenie",
            "description": "present simple, present perfect",
        },
    ]

    return (
        <div className={classes.root}>
            <AppBar position="static">
                <Tabs value={value} onChange={handleChange} aria-label="wrapped label tabs example">
                    <Tab
                        icon={<SchoolIcon/>}
                        value={1}
                        label="Zajęcia"
                        wrapped
                        {...a11yProps(1)}
                    />
                    <Tab
                        icon={<CalendarTodayIcon/>}
                        value={2}
                        label="Kalendarz"
                        wrapped
                        {...a11yProps(2)}
                    />
                    <Tab
                        icon={<ForumIcon/>}
                        value={3}
                        label="Forum"
                        wrapped
                        {...a11yProps(3)}
                    />
                </Tabs>
            </AppBar>
            <TabPanel value={value} index={1}>
                    {lessons.map((lesson, index) => (
                            <Card className={classes.root} style  = {{ marginBottom: 30}}>
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
                                            {lesson.title}
                                        </Typography>
                                        <Typography variant="body2" color="textSecondary" component="p">
                                            {lesson.description}
                                        </Typography>
                                    </CardContent>
                                </CardActionArea>
                                <CardActions>
                                    <Button size="small" onClick={() => (history.push(`/landing/lesson/`))}>Otwórz</Button>
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