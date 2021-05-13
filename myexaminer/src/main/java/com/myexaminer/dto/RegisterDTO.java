package com.myexaminer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    private String email;

    private String password;

    private String recoveryQuestion;

    private String recoveryAnswer;

    private String firstName;

    private String lastName;

    private String index;

    private String faculty;

    private String fieldOfStudy;
}
