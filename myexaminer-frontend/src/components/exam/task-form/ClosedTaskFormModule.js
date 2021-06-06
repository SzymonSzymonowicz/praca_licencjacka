import { Box, Radio, TextField } from "@material-ui/core";
import React, { useState } from "react";
import { Controller, useFieldArray } from "react-hook-form";

export default function ClosedTaskFormModule({
  control,
  errors,
  getValues,
  setValue,
}) {
  const [radioValue, setRadioValue] = useState(0);

  const { fields } = useFieldArray({
    control,
    name: "answers",
  });

  const handleRadio = (selected) => {
    getValues()["answers"].forEach((_value, index) => {
      var field = `answers.${index}.value`;
      var newValue = selected === index ? "T" : "F";

      setValue(field, newValue);
    });
  };

  return (
    <>
      {fields.map((item, index) => {
        return (
          <Box
            key={item.id}
            display="flex"
            style={{ paddingBottom: "20px", width: "50%" }}
          >
            <Controller
              control={control}
              name={`answers.${index}.value`}
              render={({ field: { onChange, onBlur, value, ...rest } }) => (
                <Radio
                  value={index}
                  name={`answers.${index}.value`}
                  checked={index === radioValue}
                  onChange={(event) => {
                    setRadioValue(parseInt(event.target.value));
                    handleRadio(index);
                  }}
                  {...rest}
                />
              )}
            />

            <Controller
              control={control}
              name={`answers.${index}.text`}
              defaultValue={item.text}
              render={({ field }) => (
                <TextField
                  variant="outlined"
                  label={`Odpowiedź nr ${index + 1}`}
                  fullWidth
                  error={errors?.answers?.[index]?.text ? true : false}
                  helperText={
                    errors?.answers?.[index]?.text
                      ? errors?.answers[index]?.text?.message
                      : null
                  }
                  {...field}
                />
              )}
              rules={{
                required: "Wypełnij to pole",
              }}
            />
          </Box>
        );
      })}
    </>
  );
}
