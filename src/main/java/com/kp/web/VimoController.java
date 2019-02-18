package com.kp.web;

import com.kp.dTO.InscriptionDTO;
import com.kp.dTO.UserDTO;
import com.kp.entities.Device;
import com.kp.entities.Inscription;
import com.kp.entities.UserApp;
import com.kp.exception.VimoException;
import com.kp.repository.DeviceRepository;
import com.kp.repository.InscriptionRepository;
import com.kp.repository.UserAppRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<Inscription> saveInscription(@RequestBody InscriptionDTO insDTO) throws VimoException{
        Inscription ins = new Inscription();
        logger.info("Inscription d'un utilisateur " );
        if (insDTO==null){
            throw new VimoException("Inscription impossible");
        }
        Device d = null;
        d=deviceRepository.findDevicesByImei(insDTO.getImei());
        if(d!=null){
            throw new VimoException("Ce device est deja utilise");
        }
        UserApp u = null;

        if(u!=null){
            throw new VimoException("Ce utilisateur existe deja");
        }
        if(u==null && d==null){
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
    public ResponseEntity<UserApp> login(@RequestBody UserDTO userdto) throws VimoException{
        logger.info("Login d'un User  " );

        if(userdto==null){
            throw new VimoException("Veuillez renseigner tous les champs");
        }
        UserApp u =null;
        u = userAppRepository.findUserAppByCniAndSecret(userdto.getPhoneNumber(),userdto.getSecret());
        if(u==null){
            throw new VimoException("Login ou mot de pass incorrect");
        }
       return new ResponseEntity<UserApp>(userAppRepository.findUserAppByCniAndSecret(userdto.getPhoneNumber(),userdto.getSecret()), HttpStatus.OK);
    }

    //Consultation Profile
    @RequestMapping(value = "/profile/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Optional<Inscription>> getProfil(@PathVariable("id") int id)throws VimoException{
        logger.info("I de l'utilisateur : " + id);
       Optional<Inscription> ins = inscriptionRepository.findById(id);
        if (ins == null || ins.get().getId() <= 0){
            throw new VimoException("Inscription inexistant");
        }
        return new ResponseEntity<Optional<Inscription>>(inscriptionRepository.findById(id), HttpStatus.OK);
    }

    // Mise à jour du Profile
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<Inscription> updateProfile(@RequestBody InscriptionDTO insdto){
        Device d = null;
        UserApp u = null;

        d= deviceRepository.findDevicesByPhoneNumber(insdto.getPhoneNumber());
        u=userAppRepository.findUserAppByCniAndSecret(insdto.getCni(),insdto.getSecret());

        if(d!=null && u!=null){
            d.setPhoneNumber(insdto.getPhoneNumber());
            u.setName(insdto.getName());
            u.setCni(insdto.getCni());
            u.setEmail(insdto.getEmail());
            u.setSecret(insdto.getSecret());
            deviceRepository.save(d);
            userAppRepository.save(u);
        }
        return null;
    }



}
