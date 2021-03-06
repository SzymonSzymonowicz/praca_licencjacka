package com.myexaminer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
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
