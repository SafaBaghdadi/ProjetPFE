package com.projetpfe.projetpfe.Service;

import com.projetpfe.projetpfe.Models.Profil;
import com.projetpfe.projetpfe.Models.UserEntity;
import com.projetpfe.projetpfe.Models.UserRole;
import com.projetpfe.projetpfe.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class UserServiceImpl implements UserServiceInterf{


    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    String ch;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    public UserEntity updateUser(Long idUser, UserEntity userEntity) {
        UserEntity usr = userRepository.findById(idUser)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable avec l'ID : " + idUser));

        usr.setUsername(userEntity.getUsername());

        // Vérifiez si un nouveau mot de passe a été fourni et hachez-le s'il y en a un
        if (userEntity.getPassword() != null && !userEntity.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(userEntity.getPassword());
            usr.setPassword(hashedPassword);
        }

        usr.setRole(userEntity.getRole());

        Profil userProfil = userEntity.getProfil();
        userProfil.setFirstname(userEntity.getProfil().getFirstname());
        userProfil.setLastname(userEntity.getProfil().getLastname());

        // Si l'utilisateur est un enseignant ou parent, mettez à jour le phone
        if (userEntity.getRole() == UserRole.ENSEIGNANT || userEntity.getRole() == UserRole.PARENT) {
            userProfil.setPhoneNumber(userEntity.getProfil().getPhoneNumber());

            // Si l'utilisateur est un enseignant, mettez à jour le cours
            if (userEntity.getRole() == UserRole.ENSEIGNANT) {
                userProfil.setMatiere(userEntity.getProfil().getMatiere());
            }

            // Si l'utilisateur est un eleve , mettez à jour l'age
            if (userEntity.getRole() == UserRole.ELEVE) {
                userProfil.setAge(userEntity.getProfil().getAge());
            }

        }

        return userRepository.save(usr);
    }



    public String deleteUser(Long idUser) {
        UserEntity userEntity = userRepository.findById(idUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable avec l'ID : " + idUser));
        String ch="User deleted";

        userRepository.delete(userEntity);
        return ch ;
    }
}