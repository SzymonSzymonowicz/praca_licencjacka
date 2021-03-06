package com.myexaminer.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "notebook")
public class Notebook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notebook_id")
    private int idNotebook;

    @Column(name = "notebook_body")
    private String notebookBody;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_account_id")
    Account account;

    public Notebook(String notebookBody, Account account) {
        this.notebookBody = notebookBody;
        this.account = account;
    }
}
