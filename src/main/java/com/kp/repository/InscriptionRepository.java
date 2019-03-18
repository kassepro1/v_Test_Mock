package com.kp.repository;


import com.kp.entities.Device;
import com.kp.entities.Inscription;
import com.kp.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscriptionRepository extends JpaRepository<Inscription,Integer> {
    public Inscription findInscriptionByDeviceAndUser(Device d, UserApp u);

}
