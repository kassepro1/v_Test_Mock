package com.kp.repository;

import com.kp.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAppRepository extends JpaRepository<UserApp,Integer> {
    public UserApp findUserAppByCni(String cni);
    public UserApp findUserAppByCniAndSecret(String cni , String secret);
}
