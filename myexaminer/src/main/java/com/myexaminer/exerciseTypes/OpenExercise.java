package com.myexaminer.exerciseTypes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OpenExercise.class, name = "O"),
        @JsonSubTypes.Type(value = ClosedExercise.class, name = "Z"),
        @JsonSubTypes.Type(value = BlanksExercise.class, name = "L"),
})
@AllArgsConstructor
@ToString
public class OpenExercise {

    protected String type;

    protected String instruction;

    protected Long points;
}
