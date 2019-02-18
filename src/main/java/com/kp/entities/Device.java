package com.kp.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String phoneNumber;
    private String imei;
    @OneToMany(cascade=CascadeType.ALL, targetEntity=Inscription.class)
    @JoinColumn(name="device")
    private List<Inscription> inscriptions;

    public Device() {
    }

    public Device(String phoneNumber, String imei, List<Inscription> inscriptions) {
        this.phoneNumber = phoneNumber;
        this.imei = imei;
        this.inscriptions = inscriptions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Inscription> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(List<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }
}
