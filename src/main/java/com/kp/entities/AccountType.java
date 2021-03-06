package com.kp.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@Entity(name = "account_type")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String libelle;
    @OneToMany(cascade=CascadeType.ALL, targetEntity=Account.class)
    @JoinColumn(name="accountType")
    private List<Account> accounts;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public AccountType(String libelle, List<Account> accounts) {
        this.libelle = libelle;
        this.accounts = accounts;
    }

    public AccountType(String libelle) {
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

    public AccountType() {
    }
}
