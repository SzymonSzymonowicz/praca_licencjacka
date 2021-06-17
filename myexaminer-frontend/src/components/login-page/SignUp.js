import React from "react";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";
import Box from "@material-ui/core/Box";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";
import Copyright from "components/login-page/Copyright";
import { Link } from "react-router-dom";
import { useHistory } from "react-router";
import { checkIsEmailUnique, checkIsIndexUnique, register } from "services/auth-service";
import { Controller, useForm } from "react-hook-form";
import { isValidEmail, isValidPassword } from "utils/validationUtils";


export default function SignUp(props) {
  const classes = props.className;
  const history = useHistory();

  const { control, formState: { errors }, reset, handleSubmit, setValue } = useForm({
    defaultValues: {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      recoveryQuestion: "",
      recoveryAnswer: "",
      index: "",
      faculty: "",
      fieldOfStudy: ""
    }
  });

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Typography component="h1" variant="h5">
          Rejestracja
        </Typography>
        <form className={classes.form} noValidate onSubmit={handleSubmit(account => {
          register(account);
          history.push("/");
        })}>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <Controller
                control={control}
                name="firstName"
                render={({ field }) =>
                  <TextField
                    autoComplete="Imię"
                    variant="outlined"
                    label="Imię"
                    autoFocus
                    required
                    fullWidth
                    error={errors.firstName ? true : false}
                    helperText={ errors.firstName ? errors.firstName?.message : null }
                    {...field}
                  />
                }
                rules={{
                  required: "Wypełnij to pole",
                }}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <Controller
                control={control}
                name="lastName"
                render={({ field }) =>
                  <TextField
                    autoComplete="Nazwisko"
                    variant="outlined"
                    label="Nazwisko"
                    autoFocus
                    required
                    fullWidth
                    error={errors.lastName ? true : false}
                    helperText={ errors.lastName ? errors.lastName?.message : null }
                    {...field}
                  />
                }
                rules={{
                  required: "Wypełnij to pole",
                }}
              />
            </Grid>
            <Grid item xs={12}>
              <Controller
                control={control}
                name="email"
                render={({ field }) =>
                  <TextField
                    autoComplete="Email"
                    variant="outlined"
                    label="Email"
                    autoFocus
                    required
                    fullWidth
                    error={errors.email ? true : false}
                    helperText={ errors.email ? errors.email?.message : null }
                    {...field}
                  />
                }
                rules={{
                  required: "Wypełnij to pole",
                  validate: checkIsEmailUnique && isValidEmail
                }}
              />
            </Grid>
            <Grid item xs={12}>
              <Controller
                control={control}
                name="password"
                render={({ field }) =>
                  <TextField
                    autoComplete="Hasło"
                    type="password"
                    variant="outlined"
                    label="Hasło"
                    autoFocus
                    required
                    fullWidth
                    error={errors.password ? true : false}
                    helperText={errors.password ? errors.password?.message : null}
                    style={{ whiteSpace: "pre" }}
                    {...field}
                  />
                }
                rules={{
                  required: "Wypełnij to pole",
                  validate: isValidPassword
                }}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <Controller
                control={control}
                name="faculty"
                render={({ field }) =>
                  <TextField
                    autoComplete="Wydział"
                    variant="outlined"
                    label="Wydział"
                    required
                    fullWidth
                    error={errors.faculty ? true : false}
                    helperText={ errors.faculty ? errors.faculty?.message : null }
                    {...field}
                  />
                }
                rules={{
                  required: "Wypełnij to pole",
                }}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <Controller
                control={control}
                name="fieldOfStudy"
                render={({ field }) =>
                  <TextField
                    autoComplete="Kierunek"
                    variant="outlined"
                    label="Kierunek"
                    required
                    fullWidth
                    error={errors.fieldOfStudy ? true : false}
                    helperText={ errors.fieldOfStudy ? errors.fieldOfStudy?.message : null }
                    {...field}
                  />
                }
                rules={{
                  required: "Wypełnij to pole",
                }}
              />
            </Grid>
            <Grid item xs={12}>
              <Controller
                control={control}
                name="index"
                render={({ field }) =>
                  <TextField
                    autoComplete="Indeks"
                    variant="outlined"
                    label="Indeks"
                    required
                    fullWidth
                    error={errors.index ? true : false}
                    helperText={ errors.index ? errors.index?.message : null }
                    {...field}
                  />
                }
                rules={{
                  required: "Wypełnij to pole",
                  validate: checkIsIndexUnique,
                  pattern: {
                    value: /^\d{6}$/,
                    message: "Indeks powinien składać się z 6 cyfr"
                  }
                }}
              />
              {console.log(errors)}
            </Grid>
            <Grid item xs={12}>
              <Controller
                control={control}
                name="recoveryQuestion"
                render={({ field }) =>
                  <TextField
                    autoComplete="Pytanie pomocniczne"
                    variant="outlined"
                    label="Pytanie pomocniczne"
                    required
                    fullWidth
                    error={errors.recoveryQuestion ? true : false}
                    helperText={ errors.recoveryQuestion ? errors.recoveryQuestion?.message : null }
                    {...field}
                  />
                }
                rules={{
                  required: "Wypełnij to pole",
                }}
              />
            </Grid>
            <Grid item xs={12}>
              <Controller
                control={control}
                name="recoveryAnswer"
                render={({ field }) =>
                  <TextField
                    autoComplete="Odpowiedź"
                    variant="outlined"
                    label="Odpowiedź"
                    required
                    fullWidth
                    error={errors.recoveryAnswer ? true : false}
                    helperText={ errors.recoveryAnswer ? errors.recoveryAnswer?.message : null }
                    {...field}
                  />
                }
                rules={{
                  required: "Wypełnij to pole",
                }}
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
          <Grid container justify="center">
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
