package com.kp.repository;

import com.kp.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device,Integer> {
    public Device findDevicesByImei(String imei);
    public Device findDevicesByPhoneNumber(String phoneNumber);
}
