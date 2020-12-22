import React from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';

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
        id: `vertical-tab-${index}`,
        'aria-controls': `vertical-tabpanel-${index}`,
    };
}

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.paper,
        display: 'flex',
        height: 400,
    },
    tabs: {
        borderRight: `1px solid ${theme.palette.divider}`,
    },
}));

export default function Lesson(props) {
    const classes = useStyles();
    const [value, setValue] = React.useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const chapter = [
        {
            "title": "Past Simple",
            "description": "<h2>Past Simple - użycie</h2>Czas Past Simple jest czasem przeszłym, który w dużym stopniu odpowiada polskiemu trybowi dokonanemu. Stosujemy go w następujących sytuacjach:<br/>" +
                "<h3>Mówiąc o czynnościach, stanach i sytuacjach, które miały miejsce w przeszłości i nie mają już żadnego związku z teraźniejszością.</h3>" +
                "W tego typu zdaniach bardzo często pojawiają się określenia czasu precyzujące moment wykonania czynności. Oto kilka przykładów:" +
                "<ul><li>Jim watched TV yesterday.</li><li>I learnt German many years ago.</li><li>They lived in the countryside when they were younger.</li>" +
                "<li>Mary wrote the report two days ago.</li><li>We visited Paris last month.</li></ul>",
        },
        {
            "title": "Past Perfect",
            "description": "<h2>Past Perfect - użycie</h2>Czasu Past Perfect Simple ma dość ograniczony zakres użycia. Stosujemy go, aby mówić o czynnościach, które zakończyły się przed inną czynnością lub punktem w czasie w przeszłości. " +
                "<br/>Takim punktem w czasie może też być data lub jakieś wydarzenie. Oto kilka przykładów.<br/>" +
                "<ul><li>I had packed my suitcases before the taxi arrived.</li><li>Peter had broken down after his wife had left him.</li><li>By the time you came we had discussed all the points on the agenda.</li>" +
                "<li>I wondered who had eaten my piece of cake.</li><li>The company had sold over twenty thousand of these cars by 1999.</li></ul>",
        }
    ]

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
                <Tab label={chapter[0].title} {...a11yProps(0)} />
                <Tab label={chapter[1].title} {...a11yProps(1)} />
            </Tabs>
            <TabPanel value={value} index={0}>
                <div dangerouslySetInnerHTML={{__html: chapter[0].description}}></div>
            </TabPanel>
            <TabPanel value={value} index={1}>
                <div dangerouslySetInnerHTML={{__html: chapter[1].description}}></div>
            </TabPanel>
        </div>
    );
}
