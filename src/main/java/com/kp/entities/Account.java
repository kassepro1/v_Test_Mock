package com.kp.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private AccountType accountType;
    @ManyToOne
    private AccountNature accountNature;
    @ManyToOne
    private UserApp user;
    private String numCompte;
    private String iban;
    private String titulaire ;
    private double amount ;
    @OneToMany(cascade=CascadeType.ALL, targetEntity=Transaction.class)
    @JoinColumn(name="debitAccountId")
    private List<Transaction> transactionsDebit ;
    @OneToMany(cascade=CascadeType.ALL, targetEntity=Transaction.class)
    @JoinColumn(name="creditAccountId")
    private List<Transaction> transactionsCredit ;

    public Account() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountNature getAccountNature() {
        return accountNature;
    }

    public void setAccountNature(AccountNature accountNature) {
        this.accountNature = accountNature;
    }

    public UserApp getUser() {
        return user;
    }

    public void setUser(UserApp user) {
        this.user = user;
    }

    public String getNumCompte() {
        return numCompte;
    }

    public void setNumCompte(String numCompte) {
        this.numCompte = numCompte;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getTitulaire() {
        return titulaire;
    }

    public void setTitulaire(String titulaire) {
        this.titulaire = titulaire;
    }

    public List<Transaction> getTransactionsDebit() {
        return transactionsDebit;
    }

    public void setTransactionsDebit(List<Transaction> transactionsDebit) {
        this.transactionsDebit = transactionsDebit;
    }

    public List<Transaction> getTransactionsCredit() {
        return transactionsCredit;
    }

    public void setTransactionsCredit(List<Transaction> transactionsCredit) {
        this.transactionsCredit = transactionsCredit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
