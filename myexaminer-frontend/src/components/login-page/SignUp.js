import React from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import {default as MuiLink} from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import { Link } from 'react-router-dom';


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


export default function SignUp(props) {
  const classes = props.className

  function registerUser(email, password, recoveryQuestion, recoveryAnswer){
    fetch('http://localhost:8080/account', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        email: email,
        password: password,
        recoveryQuestion: recoveryQuestion,
        recoveryAnswer: recoveryAnswer
      })
    }).then(function (response) {
      if (response.status === 200) {
        console.log("User REGISTERED SUCCESSFULLY!")
      } else if (response.status === 422 ){
        console.log("Given email ALREADY EXISTS!")
      } else {
        console.log("Something went wrong!")
      }
    }).catch(function (error) {
      console.log("error")
    })
  }

  const handleSubmit = event => {
    event.preventDefault();
    
    console.table([{"email": event.target.email.value, "password": event.target.password.value, "recoveryQuestion": event.target.recoveryQuestion.value, "recoveryAnswer": event.target.recoveryAnswer.value}])

    registerUser(event.target.email.value, event.target.password.value, event.target.recoveryQuestion.value, event.target.recoveryQuestion.value, event.target.recoveryAnswer.value)
  }

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Typography component="h1" variant="h5">
          Rejestracja
        </Typography>
        <form className={classes.form} noValidate onSubmit={handleSubmit}>
          <Grid container spacing={2}>
            {/*<Grid item xs={12} sm={6}>
              <TextField
                autoComplete="Imię"
                name="firstName"
                variant="outlined"
                required
                fullWidth
                id="firstName"
                label="Imię"
                autoFocus
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                variant="outlined"
                required
                fullWidth
                id="lastName"
                label="Nazwisko"
                name="lastName"
                autoComplete="Nazwisko"
              />
            </Grid>*/}
            <Grid item xs={12}>
              <TextField
                variant="outlined"
                required
                fullWidth
                id="email"
                label="Email"
                name="email"
                autoComplete="Email"
                autoFocus
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                variant="outlined"
                required
                fullWidth
                name="password"
                label="Hasło"
                type="password"
                id="password"
                autoComplete="Hasło"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                variant="outlined"
                required
                fullWidth
                name="recoveryQuestion"
                label="Pytanie pomocniczne"
                id="recoveryQuestion"
                autoComplete="Pytanie pomocniczne"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                variant="outlined"
                required
                fullWidth
                name="recoveryAnswer"
                label="Odpowiedź"
                id="recoveryAnswer"
                autoComplete="Odpowiedź"
              />
            </Grid>
            <Grid item xs={12}>
              <FormControlLabel
                control={<Checkbox value="allowExtraEmails" color="primary" />}
                label="Chcę otrzymywać powiadomienia na email."
              />
            </Grid>
          </Grid>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
          >
            Zarejestruj się
          </Button>
          <Grid container justify="flex-end">
            <Grid item>
              <Link to="/" variant="body2">
                Już posiadasz konto? Zaloguj się
              </Link>
            </Grid>
          </Grid>
        </form>
      </div>
      <Box mt={5}>
        <Copyright />
      </Box>
    </Container>
  );
}