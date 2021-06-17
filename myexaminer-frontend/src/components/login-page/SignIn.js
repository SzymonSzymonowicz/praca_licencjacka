import React, { useState } from "react";
import Avatar from "@material-ui/core/Avatar";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import { default as MuiLink } from "@material-ui/core/Link";
import Box from "@material-ui/core/Box";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import Copyright from "components/login-page/Copyright";
import { Link } from "react-router-dom";
import { useHistory } from "react-router";
import { login } from "services/auth-service";


export default function SignIn(props) {
  const classes = props.className;
  const history = useHistory();
  const [errorMsg, setErrorMsg] = useState();

  const displayErrorMessageWithTimeout = (message, seconds) => {
    setErrorMsg(message);

    setTimeout(() => {
      setErrorMsg(undefined);
    }, seconds * 1000);
  }

  const loginFailedMsg = "Błędne logowanie! Niepoprawny email i/lub hasło.";

  const handleSubmit = async (event) => {
    event.preventDefault();
    const response = await login(event.target.email.value, event.target.password.value);

    if (response.status === 200) {
      history.push("/landing");
    }  else if (response.status === 401) {
      console.log("UNAUTHORIZED!");
      displayErrorMessageWithTimeout(loginFailedMsg, 10);
    } else {
      console.log("Something went wrong!");
    }
  };

  return (
    <div className={classes.paper}>
      <Avatar className={classes.avatar}>{/*<LockOutlinedIcon />*/}</Avatar>
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
        {/* <FormControlLabel
          control={<Checkbox value="remember" color="primary" />}
          label="Zapamiętaj"
        /> */}
        <div style={{ color: "red", textAlign: "center", visibility: errorMsg ? "visible" : "hidden", minHeight: "20px"}}>
          {errorMsg}
        </div>
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
            <MuiLink variant="body2">Zapomniałeś hasła?</MuiLink>
          </Grid>
          <Grid item>
            <Link to="/SignUp">Nie posiadasz konta? Zarejestruj się!</Link>
          </Grid>
        </Grid>
        <Box mt={5}>
          <Copyright />
        </Box>
      </form>
    </div>
  );
}
