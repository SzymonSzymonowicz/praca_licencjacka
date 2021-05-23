import React from "react";
import { useForm, Controller } from "react-hook-form";
import { Box, Button, TextField } from "@material-ui/core";
import styles from "components/group/group.module.css";
import { groupByIdUrl } from "router/urls";
import authHeader from "services/auth-header";
import CloseIcon from '@material-ui/icons/Close';
import CheckIcon from '@material-ui/icons/Check';
import { checkIsGroupNameUnique } from "services/group-service";

export default function EditGroupForm(props) {
  const { control, formState: { errors }, handleSubmit } = useForm({
    defaultValues: props?.group
  });

  const editGroup = (id, group) => {
    fetch(groupByIdUrl(id), {
      method: "PATCH",
      headers: {
        ...authHeader(),
        "Content-Type": "application/json"
      },
      body: JSON.stringify(group)
    })
    .then(res => {
      if (res.status === 200) {
        props?.closeModal();
        props?.getGroup(id);
      }
      else {
        console.log("Editing group failed");
        console.log(res.text())
      }
    })
    .catch(err => { console.error(err) })
  }

  return (
    <form onSubmit={handleSubmit(data => editGroup(props?.group?.id, data))} className={styles.newGroupForm}>
      <Box style={{ minHeight: "80px" }}>
      <Controller
        control={control}
        name="name"
        defaultValue=""
        render={({ field }) =>
          <TextField
            variant="outlined"
            label="Nazwa grupy"
            error={errors.name}
            fullWidth
            helperText={ errors.name ? errors.name?.message : null }
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
          Potwierdź
        </Button >
        <Button color="secondary" variant="contained" startIcon={<CloseIcon />} onClick={ props.closeModal }>
          Anuluj
        </Button >
      </Box>
    </form>
  );
}
