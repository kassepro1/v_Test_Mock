package com.kp.dTO;

public class AccounTypeDTO {

    private int id ;
    private String libelle ;

    public AccounTypeDTO(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public AccounTypeDTO() {
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
}
