package com.myexaminer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idaccount;

    private String email;
    private String password;
    private Byte is_verificated;
    private String recovery_question;
    private String recovery_answer;
    private Byte is_lecturer;

    public Account() {
    }

    public Account(int idaccount, String email, String password, Byte is_verificated, String recovery_question, String recovery_answer, Byte is_lecturer) {
        this.idaccount = idaccount;
        this.email = email;
        this.password = password;
        this.is_verificated = is_verificated;
        this.recovery_question = recovery_question;
        this.recovery_answer = recovery_answer;
        this.is_lecturer = is_lecturer;
    }

    public int getIdaccount() { return idaccount; }

    public void setIdaccount(int idaccount) { this.idaccount = idaccount; }

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

    public Byte getIs_verificated() { return is_verificated; }

    public void setIs_verificated(Byte is_verificated) { this.is_verificated = is_verificated; }

    public String getRecovery_question() { return recovery_question; }

    public void setRecovery_question(String recovery_question) { this.recovery_question = recovery_question; }

    public String getRecovery_answer() { return recovery_answer; }

    public void setRecovery_answer(String recovery_answer) { this.recovery_answer = recovery_answer; }

    public Byte getIs_lecturer() { return is_lecturer; }

    public void setIs_lecturer(Byte is_lecturer) { this.is_lecturer = is_lecturer; }

    @Override
    public String toString() {
        return "Account{" +
                "idaccount=" + idaccount +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", is_verificated=" + is_verificated +
                ", recovery_question='" + recovery_question + '\'' +
                ", recovery_answer='" + recovery_answer + '\'' +
                ", is_lecturer=" + is_lecturer +
                '}';
    }
}
