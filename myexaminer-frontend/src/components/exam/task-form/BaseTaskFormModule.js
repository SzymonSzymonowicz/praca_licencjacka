import React from 'react';
import { Controller } from "react-hook-form";
import { Box, TextField } from "@material-ui/core";
import styles from "components/group/group.module.css";
import { isWholeNumber, isNumeric } from "utils/validationUtils";


export default function BaseTaskFormModule(control, errors, setValue) {
  return <Box style={{ minHeight: "80px" }} className={styles.lessonInputBox}>
    <Controller
      control={control}
      name="instruction"
      render={({ field }) => <TextField
        variant="outlined"
        label="Polecenie"
        error={errors.instruction ? true : false}
        fullWidth
        helperText={errors.instruction ? errors.instruction?.message : null}
        {...field} />}
      rules={{
        required: "Wypełnij to pole"
      }} />

    <Controller
      control={control}
      name="points"
      render={({ field: { onChange, ...rest } }) => <TextField
        variant="outlined"
        label="Punkty"
        error={errors.points ? true : false}
        fullWidth
        type="number"
        helperText={errors.points ? errors.points?.message : null}
        onChange={(event) => {
          var num = event.target.value;
          onChange(num);
          setValue("points", parseInt(num), { shouldValidate: true });
        } }
        {...rest} />}
      rules={{
        required: "Wypełnij to pole",
        min: {
          value: 1,
          message: "Zadanie musi być warte przynajmniej jeden punkt"
        },
        validate: {
          numeric: v => isNumeric(v) || "Podana wartość musi być liczbą",
          wholeNumber: v => isWholeNumber(v) || "Punkty muszą być liczbą całkowitą"
        }
      }} />

  </Box>;
}