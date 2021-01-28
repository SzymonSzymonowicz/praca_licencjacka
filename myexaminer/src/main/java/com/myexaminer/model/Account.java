package com.myexaminer.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int idAccount;

    private String email;
    private String password;

    @Column(name = "is_verificated")
    private boolean isVerificated;

    @Column(name = "recovery_question")
    private String recoveryQuestion;

    @Column(name = "recovery_answer")
    private String recoveryAnswer;

    @Column(name = "is_lecturer")
    private boolean isLecturer;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "fk_account_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_role_id")
    )
    private List<Role> roles = new ArrayList<>();

    public Account() {
    }

    public Account(String email, String password, String recoveryQuestion, String recoveryAnswer) {
        this.email = email;
        this.password = password;
        this.isVerificated = false;
        this.recoveryQuestion = recoveryQuestion;
        this.recoveryAnswer = recoveryAnswer;
        this.isLecturer = false;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
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

    public boolean isVerificated() {
        return isVerificated;
    }

    public void setVerificated(boolean verificated) {
        isVerificated = verificated;
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

    public boolean isLecturer() {
        return isLecturer;
    }

    public void setLecturer(boolean lecturer) {
        isLecturer = lecturer;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addToRoles(Role role){
        roles.add(role);
    }

    @Override
    public String toString() {
        return "Account{" +
                "idaccount=" + idAccount +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", is_verificated=" + isVerificated +
                ", recovery_question='" + recoveryQuestion + '\'' +
                ", recovery_answer='" + recoveryAnswer + '\'' +
                ", is_lecturer=" + isLecturer +
                '}';
    }
}
