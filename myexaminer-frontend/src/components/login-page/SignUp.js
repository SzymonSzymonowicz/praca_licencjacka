import React from "react";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import TextField from "@material-ui/core/TextField";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import Grid from "@material-ui/core/Grid";
import Box from "@material-ui/core/Box";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";
import Copyright from "components/login-page/Copyright";
import { Link } from "react-router-dom";
import { useHistory } from "react-router";
import { register } from "services/auth-service";


export default function SignUp(props) {
  const classes = props.className;
  const history = useHistory();

  const handleSubmit = (event) => {
    event.preventDefault();
    let form = event.target;

    register(
      form.email.value,
      form.password.value,
      form.recoveryQuestion.value,
      form.recoveryAnswer.value,
      form.firstName.value,
      form.lastName.value,
      form.index.value,
      form.faculty.value,
      form.fieldOfStudy.value
    )
    .then((response) => {
      if (response.status === 200) {
        console.log("User REGISTERED SUCCESSFULLY!");
        history.push("/");
      } else if (response.status === 422) {
        console.log("Given email ALREADY EXISTS!");
      } else {
        console.log("Something went wrong!");
      }
    })
    .catch((error) => {
      console.log("error: " + error);
    });
  };

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Typography component="h1" variant="h5">
          Rejestracja
        </Typography>
        <form className={classes.form} noValidate onSubmit={handleSubmit}>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
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
            </Grid>
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
            <Grid item xs={12} sm={6}>
              <TextField
                autoComplete="Wydział"
                name="faculty"
                variant="outlined"
                required
                fullWidth
                id="faculty"
                label="Wydział"
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                autoComplete="Kierunek"
                name="fieldOfStudy"
                variant="outlined"
                required
                fullWidth
                id="fieldOfStudy"
                label="Kierunek"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                autoComplete="Indeks"
                name="index"
                variant="outlined"
                required
                fullWidth
                label="Indeks"
                id="index"
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
              <Link to="/">Już posiadasz konto? Zaloguj się</Link>
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
