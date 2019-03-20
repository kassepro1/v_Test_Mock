package com.kp.entities;


import javax.persistence.*;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id ;
    @ManyToOne
    private Account debitAccountId;
    @ManyToOne
    private Account creditAccountId;
    private double amount ;
    @ManyToOne
    private TransactionType transactionType;

    public Transaction() {
    }

    public Transaction(Account debitAccountId, Account creditAccountId, double amount, TransactionType transactionType) {
        this.debitAccountId = debitAccountId;
        this.creditAccountId = creditAccountId;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getDebitAccountId() {
        return debitAccountId;
    }

    public void setDebitAccountId(Account debitAccountId) {
        this.debitAccountId = debitAccountId;
    }

    public Account getCreditAccountId() {
        return creditAccountId;
    }

    public void setCreditAccountId(Account creditAccountId) {
        this.creditAccountId = creditAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
