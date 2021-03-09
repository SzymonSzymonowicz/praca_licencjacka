package com.myexaminer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int idRole;

    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<Account> accounts = new ArrayList<>();

    public void addToAccounts(Account account){
        accounts.add(account);
    }

    public Role(String name, List<Account> accounts) {
        this.name = name;
        this.accounts = accounts;
    }
}
