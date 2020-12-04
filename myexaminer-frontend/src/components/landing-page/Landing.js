import React from 'react';
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

import Fab from '@material-ui/core/Fab';
import {default as NoteIcon} from '@material-ui/icons/LibraryBooks';
import Zoom from '@material-ui/core/Zoom';
import Tiles from './Tiles';
import { AccountCircle } from '@material-ui/icons';


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
        <Divider />
        <List>
            <ListItem button>
              <ListItemIcon><GroupIcon/></ListItemIcon>
              <ListItemText primary="Grupy" />
            </ListItem>
            <ListItem button>
              <ListItemIcon><AssignmentIcon/></ListItemIcon>
              <ListItemText primary="Egzaminy" />
            </ListItem>
        </List>
        <Divider />
        <List>
          <ListItem button>
            <ListItemIcon><AnnouncementIcon/></ListItemIcon>
            <ListItemText primary="Ogłoszenia" />
          </ListItem>
          <ListItem button>
            <ListItemIcon><EqualizerIcon/></ListItemIcon>
            <ListItemText primary="Oceny i statystyki" />
          </ListItem>
          <ListItem button>
            <ListItemIcon><GTranslateIcon/></ListItemIcon>
            <ListItemText primary="Tłumacz" />
          </ListItem>
        </List>
      </Drawer>
      <main className={classes.content}>
        <div className={classes.toolbar} />
        <Typography paragraph variant='h2'>
          Witaj! Udało Ci się zalogować!
        </Typography>
        <Tiles/>
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
