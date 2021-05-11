package com.myexaminer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Notebook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notebook_id")
    private Long idNotebook;

    @Column(name = "notebook_body", columnDefinition="longtext")
    private String notebookBody;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_account_id")
    Account account;

    public Notebook(String notebookBody, Account account) {
        this.notebookBody = notebookBody;
        this.account = account;
    }
}
