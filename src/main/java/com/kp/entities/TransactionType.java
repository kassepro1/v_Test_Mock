package com.kp.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class TransactionType {
     @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String libelle ;
    @OneToMany(cascade=CascadeType.ALL, targetEntity=TransactionType.class)
    @JoinColumn(name="transactionType")
    private List<Transaction> transactions ;

    public TransactionType() {
    }

    public TransactionType(String libelle) {
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
