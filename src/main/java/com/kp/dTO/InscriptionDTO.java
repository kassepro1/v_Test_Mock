package com.kp.dTO;

public class InscriptionDTO {
    private int idDevice;
    private int idUser;
    private String phoneNumber;
    private String imei;
    private String name;
    private String cni;
    private String secret;
    private String email;
    public InscriptionDTO() {
    }

    public InscriptionDTO(String phoneNumber, String imei, String name, String cni) {
        this.phoneNumber = phoneNumber;
        this.imei = imei;
        this.name = name;
        this.cni = cni;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCni() {
        return cni;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(int idDevice) {
        this.idDevice = idDevice;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
