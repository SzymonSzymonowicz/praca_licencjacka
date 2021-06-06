import { Button, TextField, Typography } from "@material-ui/core";
import React, { createRef, useState } from "react";
import { Controller } from "react-hook-form";

const previewStyles = {
  display: "flex",
  flexDirection: "row",
  flexWrap: "wrap",
  alignItems: "baseline",
  margin: "20px 0px",
  minHeight: "100px",
  padding: "18.5px 14px",
  border: "solid 2px black",
  borderRadius: "4px",
};

export default function BlanksTaskFormModule({ control, errors }) {
  const [fill, setFill] = useState("");
  const [caretPos, setCaretPos] = useState(0);
  const input = createRef();

  const splitted = fill.split(/(<blank>)/g);

  const splitAt = (index) => (x) => [x.slice(0, index), x.slice(index)];

  const preview = splitted?.map((str, index) =>
    str === "<blank>" ? (
      <TextField
        style={{ minWidth: "60px", margin: "0px 10px" }}
        key={`blank ${index}`}
        disabled
        contentEditable={false}
      />
    ) : (
      <Typography key={`text ${index}`}>{str}</Typography>
    )
  );

  const addBlank = () => {
    var [head, tail] = splitAt(caretPos)(fill);
    const blank = "<blank>";

    const modified = head + blank + tail;

    input.current.focus();
    setFill(modified);
  };

  const setCaret = () => {
    const newPos = input?.current?.selectionStart;

    if (newPos && newPos !== caretPos) {
      setCaretPos(newPos);
    }
  };

  return (
    <div style={{ width: "100%" }}>
      <Button
        color="primary"
        variant="contained"
        onClick={() => addBlank()}
      >
        Dodaj lukę
      </Button>
      <Controller
        control={control}
        name="fill"
        rules={{
          required: "Wypełnij to pole",
        }}
        render={({ field: { onChange, ref, value, onBlur, name } }) => (
          <TextField
            style={{ marginTop: "20px", maxWidth: "100%" }}
            label="Wypełnij treść zadania z lukami"
            variant="outlined"
            inputRef={input}
            value={fill}
            onChange={(e) => {
              onChange(e.target.value);
              setCaretPos(e.target.selectionStart);
              setFill(e.target.value);
            }}
            onKeyDown={setCaret}
            onClick={setCaret}
            error={errors.fill ? true : false}
            fullWidth
            helperText={errors.fill ? errors.fill?.message : null}
            multiline
            rows={3}
            name={name}
          />
        )}
      />

      <div style={previewStyles}>{preview}</div>
    </div>
  );
}
