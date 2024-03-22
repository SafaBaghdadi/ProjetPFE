package com.projetpfe.projetpfe.Service;

import com.projetpfe.projetpfe.Models.Profil;
import com.projetpfe.projetpfe.Models.UserEntity;
import com.projetpfe.projetpfe.Models.UserRole;
import com.projetpfe.projetpfe.Repository.ProfilRepository;
import com.projetpfe.projetpfe.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserServiceInterf {


    @Autowired
    private  UserRepository userRepository;
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private ProfilRepository profilRepository;
    public UserServiceImpl( ProfilRepository profilRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.profilRepository =profilRepository;

    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserById(long idUser) {

        return userRepository.findById(idUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable avec l'ID : " + idUser));
    }


    public UserEntity CreatedUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
    @Override
    @Transactional
    public UserEntity updateUser(Long idUser, UserEntity userEntity) {
        UserEntity usr = userRepository.findById(idUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable avec l'ID : " + idUser));

        usr.setUsername(userEntity.getUsername());
        usr.setEmail(userEntity.getEmail());
        // Vérifiez si un nouveau mot de passe a été fourni et hachez-le s'il y en a un
        if (userEntity.getPassword() != null && !userEntity.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(userEntity.getPassword());
            usr.setPassword(hashedPassword);
        }
        usr.setRole(userEntity.getRole());

        //profil
        Profil userProfil = userEntity.getProfil();
        // Vérifier si le profil de l'utilisateur est null
        if (userProfil != null) {

            Profil usrProfil = usr.getProfil();

            // Mise à jour des propriétés du profil

            usrProfil.setFirstname(userEntity.getProfil().getFirstname());
            usrProfil.setLastname(userEntity.getProfil().getLastname());


            // Si l'utilisateur est un enseignant ou parent, mettez à jour le phone
            if (userEntity.getRole() == UserRole.ENSEIGNANT || userEntity.getRole() == UserRole.PARENT) {
                usrProfil.setPhoneNumber(userEntity.getProfil().getPhoneNumber());

                // Si l'utilisateur est un enseignant, mettez à jour le cours
                if (userEntity.getRole() == UserRole.ENSEIGNANT) {
                    usrProfil.setMatiere(userEntity.getProfil().getMatiere());
                }

                // Si l'utilisateur est un eleve, mettez à jour l'age
                if (userEntity.getRole() == UserRole.ELEVE) {
                    usrProfil.setAge(userEntity.getProfil().getAge());
                }
            }

            // Assurez-vous que le profil est associé à l'utilisateur
            usrProfil.setUser(usr);


        }
        return userRepository.save(usr);
    }


    public String deleteUser(Long idUser) {
        UserEntity userEntity = userRepository.findById(idUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable avec l'ID : " + idUser));
        String ch = "User deleted";

        userRepository.delete(userEntity);
        return ch;
    }


    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable avec le nom d'utilisateur : " + username));
    }
}

