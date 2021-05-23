import React from "react";
import { useForm, Controller } from "react-hook-form";
import { Box, Button, TextField } from "@material-ui/core";
import styles from "components/group/group.module.css";
import { createGroupUrl } from "router/urls";
import authHeader from "services/auth-header";
import CloseIcon from '@material-ui/icons/Close';
import CheckIcon from '@material-ui/icons/Check';
import { checkIsGroupNameUnique } from "services/group-service";

export default function CreateGroupForm(props) {
  const { control, formState: { errors }, handleSubmit } = useForm();

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
            fullWidth
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
      <Box display="flex" justifyContent="space-evenly">
        <Button color="primary" type="submit" variant="contained" startIcon={<CheckIcon />} style={{ marginRight: "20px" }}>
          Utwórz grupę
        </Button >
        <Button color="secondary" variant="contained" startIcon={<CloseIcon />} onClick={ props.closeModal }>
          Anuluj
        </Button >
      </Box>
    </form>
  );
}
