package com.kp.repository;

import com.kp.entities.AccountNature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountNatureRepository extends JpaRepository<AccountNature,Integer> {
}
