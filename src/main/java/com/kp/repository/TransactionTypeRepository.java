package com.kp.repository;

import com.kp.entities.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeRepository extends JpaRepository<TransactionType,Integer> {

    public TransactionType findTransactionTypeByLibelle(String libelle);
}
