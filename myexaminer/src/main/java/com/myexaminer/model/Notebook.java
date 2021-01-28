package com.myexaminer.model;

import javax.persistence.*;

@Entity
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

    public int getIdNotebook() {
        return idNotebook;
    }

    public void setIdNotebook(int idNotebook) {
        this.idNotebook = idNotebook;
    }

    public String getNotebookBody() {
        return notebookBody;
    }

    public void setNotebookBody(String notebookBody) {
        this.notebookBody = notebookBody;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Notebook() {
    }

    public Notebook(String notebookBody, Account account) {
        this.notebookBody = notebookBody;
        this.account = account;
    }
}
