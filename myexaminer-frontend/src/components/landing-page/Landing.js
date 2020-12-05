import React, { useState } from 'react';
import clsx from 'clsx';
import { AppBar, CssBaseline, Divider, Drawer, IconButton, List, ListItem, ListItemIcon, ListItemText, makeStyles, Toolbar, Typography, useTheme } from '@material-ui/core';

import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import GroupIcon from '@material-ui/icons/Group';
import AnnouncementIcon from '@material-ui/icons/Announcement';
import EqualizerIcon from '@material-ui/icons/Equalizer';
import GTranslateIcon from '@material-ui/icons/GTranslate';
import AssignmentIcon from '@material-ui/icons/Assignment';
import HomeIcon from '@material-ui/icons/Home';

import Fab from '@material-ui/core/Fab';
import {default as NoteIcon} from '@material-ui/icons/LibraryBooks';
import Zoom from '@material-ui/core/Zoom';
import Tiles from './Tiles';
import { AccountCircle } from '@material-ui/icons';
import { Route, Switch, useHistory, useRouteMatch } from 'react-router-dom';


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
  }
}));

export default function Landing(props) {
  
  const classes = useStyles();
  const theme = useTheme();
  const [open, setOpen] = React.useState(false);
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
        "subpage": "group"
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
    ]
  ]

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  const transitionDuration = {
    enter: theme.transitions.duration.enteringScreen,
    exit: theme.transitions.duration.leavingScreen,
  };

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
          <IconButton
                aria-label="account of current user"
                aria-controls="menu-appbar"
                aria-haspopup="true"
                //TODO onClick={}
                color="inherit"
              >
              <AccountCircle fontSize="large"/>
          </IconButton>
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
          return <div key={listIndex}>
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
          </div>
        })}
      </Drawer>
      <main className={classes.content}>
        {/* Place holder for toolbar so that content isn't overlaped by appbar */}
        <div className={classes.toolbar} />
          <Switch>
            <Route exact path={`${match.path}`}>
              <Typography paragraph variant='h2'>
                Witaj! Udało Ci się zalogować!
              </Typography>
              <Tiles setSelectedIndex={setSelectedIndex}/>
            </Route>
            <Route path={`${match.path}/group`}>
              <h1>Grupy</h1>
            </Route>
            <Route path={`${match.path}/exams`}>
              <h1>Egzaminy</h1>
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
          </Switch>
      </main>
      <Zoom
          in = {true}
          timeout={transitionDuration}
          style={{
            transitionDelay: `${transitionDuration.exit}ms`,
          }}
        >
          <Fab aria-label='Notepad' className={classes.fab} color='primary'>
            <NoteIcon/>
          </Fab>
        </Zoom>
    </div>
  )
}
