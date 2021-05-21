import React from "react";
import { useForm, Controller } from "react-hook-form";
import Input from "@material-ui/core/Input";
import { Box, Button, makeStyles, TextField } from "@material-ui/core";
import styles from "components/group/group.module.css";
import { groupIsUniqueNameUrl, createGroupUrl } from "router/urls";
import authHeader from "services/auth-header";

const useStyles = makeStyles((theme) => ({
  form: {
    margin: theme.spacing(4, 4),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  }
}));

export default function CreateGroupForm(props) {
  const { control, formState: { errors }, handleSubmit } = useForm();
  const onSubmit = data => console.log(data);

  const classes = useStyles();

  const checkIsGroupNameUnique = async (name) => {
    const isUnique = await fetch(groupIsUniqueNameUrl(name), {
      method: "GET",
      headers: authHeader()
    })
    .then(res => res.json())
    .catch(err => { console.error(err) })
    
    return isUnique || "Podana nazwa grupy jest już zajęta, podaj inną nazwę"
  };

  const createGroup = (name) => {
    fetch(createGroupUrl, {
      method: "POST",
      headers: {
        ...authHeader(),
        "Content-Type": "application/json"
      },
      body: name
    })
      .then(res => {
        console.log(props?.getGroups);
        console.log("input from form");
        console.log(name)
        if (res.status === 200) {
          props?.getGroups();
          props?.closeModal();
        }
        else {
          console.log("Adding group failed");
          console.log(res.text())
        }
    })
    .catch(err => { console.error(err) })
  }

  return (
    <form onSubmit={handleSubmit(data => createGroup(data.groupName))} className={styles.newGroupForm}>
      <Box style={{ minHeight: "80px" }}>
      <Controller
        control={control}
        name="groupName"
        defaultValue=""
        render={({ field }) =>
          <TextField
            variant="outlined"
            label="Nazwa grupy"
            error={errors.groupName}
            helperText={ errors.groupName ? errors.groupName?.message : null }
            {...field}
          />
        }
        rules={{
          required: "Wypełnij to pole",
          validate: checkIsGroupNameUnique
        }}
      />
      </Box>
      <Button color="primary" type="submit" variant="contained">
        Utwórz grupę
      </Button >
    </form>
  );
}
