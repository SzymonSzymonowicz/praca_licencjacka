package com.myexaminer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Notebook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "longtext")
    private String content;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_account_id")
    Account account;

    public Notebook(String content, Account account) {
        this.content = content;
        this.account = account;
    }
}
