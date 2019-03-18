package com.kp.web;

import com.kp.dTO.AccountDTO;
import com.kp.dTO.InscriptionDTO;
import com.kp.dTO.UserDTO;
import com.kp.entities.*;
import com.kp.exception.VimoException;
import com.kp.mapper.AccountMapper;
import com.kp.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@RestController
public class VimoController {
    private static final Logger logger = LoggerFactory.getLogger(VimoController.class);
    @Autowired
    private InscriptionRepository inscriptionRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserAppRepository userAppRepository;
    @Autowired
    private AccountTypeRepository accountTypeRepository;
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<Inscription> saveInscription(@RequestBody InscriptionDTO insDTO) throws VimoException {
        Inscription ins = new Inscription();
        logger.info("Inscription d'un utilisateur ");
        if (insDTO == null) {
            throw new VimoException("Inscription impossible");
        }
        Device d = null;
        d = deviceRepository.findDevicesByImei(insDTO.getImei());
        if (d != null) {
            throw new VimoException("Ce device est deja utilise");
        }
        UserApp u = null;

        if (u != null) {
            throw new VimoException("Ce utilisateur existe deja");
        }
        if (u == null && d == null) {
            u = new UserApp();
            d = new Device();
            //Recuperation des donnes de l'utiisateur
            u.setCni(insDTO.getCni());
            u.setName(insDTO.getName());
            //Recuperations des donnees du Device
            d.setImei(insDTO.getImei());
            d.setPhoneNumber(insDTO.getPhoneNumber());
            //Insertion du device et de utilisteur
            deviceRepository.save(d);
            userAppRepository.save(u);

            //Creation de l'inscription
            ins.setDevice(d);
            ins.setUser(u);
            inscriptionRepository.save(ins);


        }

        return new ResponseEntity<Inscription>(inscriptionRepository.save(ins), HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserApp> login(@RequestBody UserDTO userdto) throws VimoException {
        logger.info("Login d'un User  ");

        if (userdto == null) {
            throw new VimoException("Veuillez renseigner tous les champs");
        }
        UserApp u = null;
        u = userAppRepository.findUserAppByCniAndSecret(userdto.getPhoneNumber(), userdto.getSecret());
        if (u == null) {
            throw new VimoException("Login ou mot de pass incorrect");
        }
        return new ResponseEntity<UserApp>(userAppRepository.findUserAppByCniAndSecret(userdto.getPhoneNumber(), userdto.getSecret()), HttpStatus.OK);
    }

    //Consultation Profile
    @RequestMapping(value = "/profile/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Optional<Inscription>> getProfil(@PathVariable("id") int id) throws VimoException {
        logger.info("I de l'utilisateur : " + id);
        Optional<Inscription> ins = inscriptionRepository.findById(id);
        if (ins == null || ins.get().getId() <= 0) {
            return new ResponseEntity<Optional<Inscription>>(ins,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Inscription>>(inscriptionRepository.findById(id), HttpStatus.OK);
    }

    // Mise à jour du Profile
    @RequestMapping(value = "/profile", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity<Inscription> updateProfile(@RequestBody InscriptionDTO insdto) {
        System.out.println("inscriptiondto " + insdto.getIdDevice() + "/" + insdto.getIdUser());
        Optional<Device> d = deviceRepository.findById(insdto.getIdDevice());
        Optional<UserApp> u = userAppRepository.findById(insdto.getIdUser());

        Inscription ins = null;
        if (d != null && u != null) {
            d.get().setPhoneNumber(insdto.getPhoneNumber());
            u.get().setName(insdto.getName());
            u.get().setCni(insdto.getCni());
            u.get().setEmail(insdto.getEmail());
            u.get().setSecret(insdto.getSecret());
            deviceRepository.save(d.get());
            userAppRepository.save(u.get());
            ins = inscriptionRepository.findInscriptionByDeviceAndUser(d.get(), u.get());
            if(ins==null){
                return new ResponseEntity<Inscription>(ins,HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<Inscription>(ins, HttpStatus.OK);
    }

    // Création d'un compte
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<Account> addAccount(@RequestBody AccountDTO acdto) throws VimoException {
        UserApp u = null;
        AccountType act = null;
        Account a = new Account();
        if(acdto==null){
            throw new VimoException("Donne incomplete");
        }
        u = userAppRepository.findUserAppByCni(acdto.getUserId());
        if(u==null){
            throw  new VimoException("Cet utilisateur n'existe pas ");
        }
        System.out.println(acdto.getAccounType());
        act =accountTypeRepository.findAccountTypeByLibelle(acdto.getAccounType());
        if(act==null){
            throw new VimoException("Accounte Type Invalide ");
        }
        a.setAccountType(act);
        a.setIban(acdto.getIban());
        a.setUser(u);
        a.setTitulaire(acdto.getTitulaire());
        accountRepository.save(a);

        return new ResponseEntity<Account>(accountRepository.save(a), HttpStatus.OK);
    }
    // Modification d'un compte
    @RequestMapping(value = "/account", method = RequestMethod.PUT)
    public ResponseEntity<Account> updateAccount(@RequestBody AccountDTO acdto) throws VimoException {
        return null;
    }
    //Liste des comptes beneficiaires
    @RequestMapping(value = "/account/all_recipients/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<AccountMapper>> getAllRecipientsAccount(@PathVariable("id")int id ){
        return new ResponseEntity<List<AccountMapper>>(accountRepository.getAllRecipientsAccount(id), HttpStatus.FOUND);
    }
    //Liste des comptes personnels
    @RequestMapping(value = "/account/all_personal/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<AccountMapper>> getAllPersonalAccount(@PathVariable("id")int id ){
        return new ResponseEntity<List<AccountMapper>>(accountRepository.getAllPersonalAccount(id), HttpStatus.FOUND);
    }
    // Liste des comptes collects
    @RequestMapping(value = "/account/all_group/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<AccountMapper>> getAllGroupAccount(@PathVariable("id")int id ){
        return new ResponseEntity<List<AccountMapper>>(accountRepository.getAllGroupAccount(id), HttpStatus.FOUND);
    }
}
