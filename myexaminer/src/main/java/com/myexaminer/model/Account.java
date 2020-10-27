package com.myexaminer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int account_id;

    private String email;
    private String password;
    private String recoveryQuestion;
    private String recoveryAnswer;

    public Account() {
    }

    public Account(int account_id, String email, String password, String recoveryQuestion, String recoveryAnswer) {
        this.account_id = account_id;
        this.email = email;
        this.password = password;
        this.recoveryQuestion = recoveryQuestion;
        this.recoveryAnswer = recoveryAnswer;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

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

    @Override
    public String toString() {
        return "Account{" +
                "account_id=" + account_id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", recoveryQuestion='" + recoveryQuestion + '\'' +
                ", recoveryAnswer='" + recoveryAnswer + '\'' +
                '}';
    }
}
