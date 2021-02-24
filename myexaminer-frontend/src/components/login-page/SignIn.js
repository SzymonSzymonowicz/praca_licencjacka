import React from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import {default as MuiLink} from '@material-ui/core/Link';
import Box from '@material-ui/core/Box';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { Link } from 'react-router-dom';
import { useHistory } from "react-router";

function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {'Copyright © '}
      <MuiLink color="inherit" href="https://material-ui.com/">
        MyExaminer.pl
      </MuiLink>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}


export default function SignIn(props) {
  const classes = props.className
  const history = useHistory()

  const loginUser = (email, password) => {
    fetch('http://localhost:8080/account/login', {
      method: 'POST',
      headers:{
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization':'Basic ' + window.btoa(email + ":" + password)
      },
      body: JSON.stringify({
        email: email,
        password: password
      })
     }).then((response) => {
      if (response.status === 200) {
        console.log("User loggged in!")
        sessionStorage.setItem('USER_SESSION_EMAIL', email)
        sessionStorage.setItem('USER_SESSION_PASSWORD', password)
        getRole();
        history.push("/landing")
      } else if (response.status === 401 ){
        console.log("UNAUTHORIZED!")
      } else {
        console.log("Something went wrong!")
      }
    })
  }
    /*sessionStorage.setItem('USER_SESSION_ROLE', email)*/
    const getRole = () => {
        fetch('http://localhost:8080/account/getRole', {
            method: 'GET',
            headers:{
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization':'Basic ' + window.btoa(sessionStorage.getItem('USER_SESSION_EMAIL') + ":" + sessionStorage.getItem('USER_SESSION_PASSWORD'))
            }
        }).then((response) => {
            if (response.status === 200) {
                return response.json()
            } else if (response.status === 401 ){
                console.log("UNAUTHORIZED!")
            } else {
                console.log("Something went wrong!")
            }
        }).then(roleJSON => {
            let roles = []
            roleJSON.map(userRole => {
                roles.push(userRole["name"])
            })
            sessionStorage.setItem('USER_SESSION_ROLE', roles.pop())
        })
    }

  const handleSubmit = event => {
    event.preventDefault();
    
    console.table([{"email": event.target.email.value, "password": event.target.password.value}])

    loginUser(event.target.email.value, event.target.password.value)
  }

  return (
    <div className={classes.paper}>            
      <Avatar className={classes.avatar}>
            {/*<LockOutlinedIcon />*/}
      </Avatar>
      <Typography component="h1" variant="h5">
        Logowanie
      </Typography>
      <form className={classes.form} noValidate onSubmit={handleSubmit}>
        <TextField
          variant="outlined"
          margin="normal"
          required
          fullWidth
          id="email"
          label="Email"
          name="email"
          autoComplete="email"
          autoFocus
        />
        <TextField
          variant="outlined"
          margin="normal"
          required
          fullWidth
          name="password"
          label="Hasło"
          type="password"
          id="password"
          autoComplete="current-password"
        />
        <FormControlLabel
          control={<Checkbox value="remember" color="primary" />}
          label="Zapamiętaj"
        />
        <Button
          type="submit"
          fullWidth
          variant="contained"
          color="primary"
          className={classes.submit}
        >
          Zaloguj się
        </Button>
        <Grid container>
          <Grid item xs>
            <MuiLink variant="body2">
              Zapomniałeś hasła?
            </MuiLink>
          </Grid>
          <Grid item>
              <Link to="/SignUp">
              Nie posiadasz konta? Zarejestruj się!
              </Link>
          </Grid>
        </Grid>
        <Box mt={5}>
          <Copyright />
        </Box>
      </form>
    </div>
  );
}