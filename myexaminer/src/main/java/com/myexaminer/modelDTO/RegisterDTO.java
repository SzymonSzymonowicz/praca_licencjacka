package com.myexaminer.modelDTO;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRecoveryQuestion() {
        return recoveryQuestion;
    }

    public void setRecoveryQuestion(String recoveryQuestion) {
        this.recoveryQuestion = recoveryQuestion;
    }

    public String getRecoveryAnswer() {
        return recoveryAnswer;
    }

    public void setRecoveryAnswer(String recoveryAnswer) {
        this.recoveryAnswer = recoveryAnswer;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public RegisterDTO() {
    }

    public RegisterDTO(String email, String password, String recoveryQuestion, String recoveryAnswer, String firstName, String lastName, String index, String faculty, String fieldOfStudy) {
        this.email = email;
        this.password = password;
        this.recoveryQuestion = recoveryQuestion;
        this.recoveryAnswer = recoveryAnswer;
        this.firstName = firstName;
        this.lastName = lastName;
        this.index = index;
        this.faculty = faculty;
        this.fieldOfStudy = fieldOfStudy;
    }
}
