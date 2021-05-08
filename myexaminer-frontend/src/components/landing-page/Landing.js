import React, { useState, useEffect } from 'react';
import clsx from 'clsx';
import { AppBar, CssBaseline, Divider, Drawer, IconButton, List, ListItem, ListItemIcon, ListItemText, makeStyles, Menu, MenuItem, Toolbar, Typography, useTheme } from '@material-ui/core';

import {
  Menu as MenuIcon, ChevronLeft as ChevronLeftIcon, ChevronRight as ChevronRightIcon,
  Group as GroupIcon, Announcement as AnnouncementIcon, Equalizer as EqualizerIcon,
  GTranslate as GTranslateIcon, Assignment as AssignmentIcon, Home as HomeIcon, AccountCircle,
  DoneOutline as DoneOutlineIcon
 } from '@material-ui/icons'

import Tiles from './Tiles';
import Notepad from './Notepad'
import { Route, Switch, useHistory, useRouteMatch} from 'react-router-dom';
import Exams from 'components/subpages/Exams';
import Exam from 'components/exam/Exam';
import Groups from "../subpages/Groups";
import Lesson from "../group/Lesson";
import ExamResults from 'components/exam/ExamResults';
import ExamsToCheck from 'components/lecturer/ExamsToCheck';
import CheckExam from 'components/lecturer/CheckExam';
import { examUrl } from 'router/urls';
import { logout, getCurrentAccount, hasRole } from "services/auth-service";
import authHeader from 'services/auth-header';


const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  appBarShift: {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  menuButton: {
    marginRight: 36,
  },
  hide: {
    display: 'none',
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
    whiteSpace: 'nowrap',
    justifyContent: 'center'
  },
  drawerOpen: {
    width: drawerWidth,
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  drawerClose: {
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    overflowX: 'hidden',
    width: theme.spacing(7) + 1,
    [theme.breakpoints.up('sm')]: {
      width: theme.spacing(9) + 1,
    },
  },
  toolbar: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
  },
  content: {
    flexGrow: 1,
    padding: theme.spacing(3),
  },
  fab: {
    position: 'fixed',
    bottom: theme.spacing(2),
    right: theme.spacing(2),
  },
  title: {
    flexGrow: 1,
  },
  popPaper: {
    width: "40%",
  }
}));

export default function Landing() {
  const [exams, setExams] = useState([])

  const groupId = 1
  useEffect(() => {
    fetch(examUrl + groupId, {
      method: 'GET',
      headers: authHeader()
    }).then(function (response) {
      if (response.status === 200) {
        return response.json()
      } else {
        console.log("Something went wrong!")
      }
    }).then( examsJson => {
      console.log(examsJson)
      setExams(examsJson)
    }).catch(function (error) {
      console.log("error")
    })
  }, [groupId])

  const classes = useStyles();
  const theme = useTheme();
  const [open, setOpen] = useState(false);
  const [selectedIndex, setSelectedIndex] = useState(0);

  let match = useRouteMatch();
  const history = useHistory();

  const itemLists = [
    [
      {
        "name": "Menu",
        "icon": <HomeIcon/>,
        "subpage": ""
      },
      {
        "name": "Grupy",
        "icon": <GroupIcon/>,
        "subpage": "groups"
      },
      {
        "name": "Egzaminy",
        "icon": <AssignmentIcon/>,
        "subpage": "exams"
      }
    ],
    [
      {
        "name": "Ogłoszenia",
        "icon": <AnnouncementIcon/>,
        "subpage": "announcments"
      },
      {
        "name": "Oceny i statystyki",
        "icon": <EqualizerIcon/>,
        "subpage": "grades"
      },
      {
        "name": "Tłumacz / Słownik",
        "icon": <GTranslateIcon/>,
        "subpage": "dictionary"
      }
    ],
    [
      {
        "name": "Oceń egzaminy",
        "icon": <DoneOutlineIcon/>,
        "subpage": "examstocheck"
      }
    ]
  ]

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  const [anchorEl, setAnchorEl] = React.useState(null);
  const openAccount = Boolean(anchorEl);

  const handleMenuAnchor = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const accountEmail = getCurrentAccount().email;

  const isLecturer = hasRole("ROLE_LECTURER");

  return (
    <div className={classes.root}>
      <CssBaseline />
      <AppBar
        position="fixed"
        className={clsx(classes.appBar, {
          [classes.appBarShift]: open,
        })}
      >
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            edge="start"
            className={clsx(classes.menuButton, {
              [classes.hide]: open,
            })}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" noWrap className={classes.title}>
            My Examiner
          </Typography>
          <Typography>{accountEmail ? accountEmail : ""}</Typography>
          <div>
            <IconButton
                  aria-label="account of current user"
                  aria-controls="menu-appbar"
                  aria-haspopup="true"
                  onClick={handleMenuAnchor}
                  color="inherit"
                >
                <AccountCircle fontSize="large"/>
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorEl}
              getContentAnchorEl={null}
              anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'center',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'bottom',
                horizontal: 'left',
              }}
              open={openAccount}
              onClose={handleMenuClose}
            >
              <MenuItem onClick={handleMenuClose}>Moje konto</MenuItem>
              <MenuItem onClick={() => {
                handleMenuClose();
                logout();
                history.push("/");
                }}>Wyloguj</MenuItem>
            </Menu>
          </div>
        </Toolbar>
      </AppBar>
      <Drawer
        variant="permanent"
        className={clsx(classes.drawer, {
          [classes.drawerOpen]: open,
          [classes.drawerClose]: !open,
        })}
        classes={{
          paper: clsx({
            [classes.drawerOpen]: open,
            [classes.drawerClose]: !open,
          }),
        }}
      >
        <div className={classes.toolbar}>
          <IconButton onClick={handleDrawerClose}>
            {theme.direction === 'rtl' ? <ChevronRightIcon /> : <ChevronLeftIcon />}
          </IconButton>
        </div>
        {itemLists.map((itemList, listIndex, array) => {
          let prevListLength = 0
          if(listIndex !== 0)
            prevListLength = array[listIndex - 1].length
          if(listIndex !== array.length-1 || isLecturer)
            return (<div key={listIndex}>
              <Divider />
              <List>
                {itemList.map((item, index) => (
                  <ListItem button
                    key={index} 
                    selected={selectedIndex === index + prevListLength}
                    onClick={() => {
                      setSelectedIndex(index + prevListLength);
                      history.push(`${match.path}/${item.subpage}`)
                  }}>
                    <ListItemIcon>{item.icon}</ListItemIcon>
                    <ListItemText primary={item.name}/>
                  </ListItem>
                ))}
              </List>
            </div>)
          else
            return
        })}
      </Drawer>
      <main className={classes.content}>
        {/* Place holder for toolbar so that content isn't overlaped by appbar */}
        <div className={classes.toolbar} />
          <Switch>
            <Route exact path={`${match.path}`}>
              <Typography paragraph variant='h2'>
                {`Witaj ${getCurrentAccount()?.email} !`}
              </Typography>
              <Tiles setSelectedIndex={setSelectedIndex}/>
            </Route>
            <Route path={`${match.path}/groups`}>
              <Groups/>
            </Route>
            <Route path={`${match.path}/exams`}>
              <Exams exams={exams}/>
            </Route>
            <Route path={`${match.path}/announcments`}>
              <h1>Ogłoszenia</h1>
            </Route>
            <Route path={`${match.path}/grades`}>
              <h1>Oceny</h1>
            </Route>
            <Route path={`${match.path}/dictionary`}>
              <h1>Słownik</h1>
            </Route>
            <Route path={`${match.path}/exam/:id`}>
              <Exam/>
            </Route>
            <Route path={`${match.path}/examresults/:id`}>
              <ExamResults/>
            </Route>
            {isLecturer && <Route path={`${match.path}/examstocheck`}>
              <ExamsToCheck/>
            </Route>}
            {isLecturer && <Route path={`${match.path}/checkexam/:id`}>
              <CheckExam/>
            </Route>}
            <Route path={`${match.path}/lesson/`}>
              <Lesson/>
            </Route>
          </Switch>
      </main>
      <Notepad classes={classes}/>
    </div>
  )
}
