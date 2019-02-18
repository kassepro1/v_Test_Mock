package com.kp.dTO;

public class UserDTO {
    String phoneNumber;
    String secret ;

    public UserDTO(String phoneNumber, String secret) {
        this.phoneNumber = phoneNumber;
        this.secret = secret;
    }

    public UserDTO() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
