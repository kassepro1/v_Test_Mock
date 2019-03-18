package com.kp.dTO;

public class AccountDTO {
    private int id;
    private String userId;
   private String iban;
   private String accounType ;
   private String titulaire;

    public AccountDTO() {
    }

    public AccountDTO(int id, String userId, String iban, String iban1) {
        this.id = id;
        this.userId = userId;
        this.iban = iban;
        this.iban = iban1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getAccounType() {
        return accounType;
    }

    public void setAccounType(String accounType) {
        this.accounType = accounType;
    }

    public String getTitulaire() {
        return titulaire;
    }

    public void setTitulaire(String titulaire) {
        this.titulaire = titulaire;
    }
}
