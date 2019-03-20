package com.kp.repository;

import com.kp.entities.Account;
import com.kp.mapper.AccountMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    @Query(nativeQuery = true, value =
            " SELECT " +
                    " c.num_compte as numero, at.libelle as libelle " +
                    " FROM " +
                    " account c , account_type at ,account_nature as an  " +
                    " WHERE c.account_type_id=at.id and c.account_nature_id=an.id and an.libelle like '%Beneficiaire%' " +
                    " and c.user_id=:id")
    public List<AccountMapper> getAllRecipientsAccount(@Param("id") int id);
    @Query(nativeQuery = true, value =
            " SELECT " +
                    " c.num_compte as numero, at.libelle as libelle " +
                    " FROM " +
                    " account c , account_type at ,account_nature as an  " +
                    " WHERE c.account_type_id=at.id and c.account_nature_id=an.id and an.libelle like '%Personnel%' " +
                    " and c.user_id=:id")
    public List<AccountMapper> getAllPersonalAccount(@Param("id") int id);
    @Query(nativeQuery = true, value =
            " SELECT " +
                    " c.num_compte as numero, at.libelle as libelle " +
                    " FROM " +
                    " account c , account_type at ,account_nature as an  " +
                    " WHERE c.account_type_id=at.id and c.account_nature_id=an.id and an.libelle like '%Collect%' " +
                    " and c.user_id=:id")
    public List<AccountMapper> getAllGroupAccount(@Param("id") int id);

    public  Account findAccountByNumCompte(String numCompte);
}
