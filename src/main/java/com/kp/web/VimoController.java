package com.kp.web;

import com.kp.dTO.*;
import com.kp.entities.*;
import com.kp.exception.VimoException;
import com.kp.mapper.AccountMapper;
import com.kp.repository.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(description = "API permettanr de gerer les utilisateurs et les transactions ")
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
    @Autowired
    private TransactionTypeRepository transactionTypeRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @ApiOperation(value = "Cette methode permet d'inscrire un client")
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
            u.setSecret("passer");
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
    @ApiOperation(value = "Cette methode permet d'effectuer une authentification")
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
    @ApiOperation(value = "Cette methode permet de consulter un profit via son ID")
    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public ResponseEntity<Optional<Inscription>> getProfil(@PathVariable("id") int id) throws VimoException {
        logger.info("I de l'utilisateur : " + id);
        Optional<Inscription> ins = inscriptionRepository.findById(id);
        if (ins == null || ins.get().getId() <= 0) {
            return new ResponseEntity<Optional<Inscription>>(ins,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Inscription>>(inscriptionRepository.findById(id), HttpStatus.OK);
    }

    // Mise à jour du Profile
    @ApiOperation(value = "Cette methode permet la mise a jour d'un profile")
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
    @ApiOperation(value = "Cette methode permet de creer un compte")
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<Account> addAccount(@RequestBody AccountDTO acdto) throws VimoException {
        UserApp u = null;
        AccountType act = null;
        Account a = new Account();
        if(acdto==null){
            throw new VimoException("Donne incomplete");
        }
        u = userAppRepository.findById(acdto.getId()).get();
       // u = userAppRepository.findUserAppByCni(acdto.getUserId());
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
    @ApiOperation(value = "Cette methode permet de modifier un compte")
    @RequestMapping(value = "/account", method = RequestMethod.PUT)
    public ResponseEntity<Account> updateAccount(@RequestBody AccountDTO acdto) throws VimoException {
        return null;
    }
    //Liste des comptes beneficiaires
    @ApiOperation(value = "Cette methode permet de lister les comptes beneficiaires")
    @RequestMapping(value = "/account/all_recipients/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<AccountMapper>> getAllRecipientsAccount(@PathVariable("id")int id ){
        return new ResponseEntity<List<AccountMapper>>(accountRepository.getAllRecipientsAccount(id), HttpStatus.FOUND);
    }
    //Liste des comptes personnels
    @ApiOperation(value = "Cette methode permet de lister les comptes personnels")
    @RequestMapping(value = "/account/all_personal/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<AccountMapper>> getAllPersonalAccount(@PathVariable("id")int id ){
        return new ResponseEntity<List<AccountMapper>>(accountRepository.getAllPersonalAccount(id), HttpStatus.FOUND);
    }
    // Liste des comptes collects
    @ApiOperation(value = "Cette methode permet de lister les comptes collects")
    @RequestMapping(value = "/account/all_group/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<AccountMapper>> getAllGroupAccount(@PathVariable("id")int id ){
        return new ResponseEntity<List<AccountMapper>>(accountRepository.getAllGroupAccount(id), HttpStatus.FOUND);
    }
    //Transactions
    @ApiOperation(value = "Cette methode permet de faire une transaction ")
    @PostMapping(value = "/transaction")
    @Transactional
    public ResponseEntity<Transaction> transaction(@RequestBody TransactionDTO tdto) throws VimoException {
        Account debit;
        Account credit ;
        Transaction t ;
        TransactionType tType ;

        tType = transactionTypeRepository.findTransactionTypeByLibelle(tdto.getTransactionType());

        if(tType==null){
            tType = new TransactionType();
            tType.setLibelle(tdto.getTransactionType());
            transactionTypeRepository.save(tType);
        }

        if(tdto.getTransactionType().equalsIgnoreCase("Virement")){
            if(tdto!=null){
                debit = new Account();
                System.out.println("****Num Debit compte debit exist =: "+tdto.getDebitAccountId());
                debit = accountRepository.findAccountByNumCompte(tdto.getDebitAccountId());
                credit = accountRepository.findAccountByNumCompte(tdto.getCreditAccountId());
                System.out.println("****Acount debit exist =: "+debit.getNumCompte());
                if(debit==null){
                    throw  new VimoException("Compte a debiter inexistant !");
                }
                if(credit==null){
                    throw  new VimoException("Compte a crediter inexsitant !");
                }

                if(debit.getAmount()>=tdto.getAmount()){
                    debit.setAmount(debit.getAmount()-tdto.getAmount());
                    accountRepository.save(debit);
                }else{
                    throw  new VimoException("Solde insuffisant !");
                }

                credit.setAmount(credit.getAmount()+tdto.getAmount());
                accountRepository.save(credit);

                t = new Transaction();
                t.setAmount(tdto.getAmount());
                t.setCreditAccountId(credit);
                t.setDebitAccountId(debit);
                t= transactionRepository.save(t);
                return new ResponseEntity<Transaction>(t,HttpStatus.OK);

            }else{
                throw  new VimoException("Transaction impossioble !");
            }
        }
        return null;
    }
    @ApiOperation(value = "Cette methode creer un type de compte ")
    @PostMapping("/addAccounType")
    public  ResponseEntity<AccountType> addAccountType(@RequestBody AccounTypeDTO acdto){


             AccountType at = new AccountType();

             at.setLibelle(acdto.getLibelle());
             accountTypeRepository.save(at);
        return new ResponseEntity<AccountType>(at,HttpStatus.OK);
    }

    // Liste des Type de compte
    @ApiOperation(value = "Cette methode permet de lister les types de comptes")
    @RequestMapping(value = "/acconTypes", method = RequestMethod.GET)
    public ResponseEntity<List<AccountType>> getAllAccountType( ){
        return new ResponseEntity<List<AccountType>>(accountTypeRepository.findAll(), HttpStatus.FOUND);
    }
}
