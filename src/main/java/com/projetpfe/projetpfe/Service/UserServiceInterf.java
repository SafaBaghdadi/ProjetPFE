package com.projetpfe.projetpfe.Service;

import com.projetpfe.projetpfe.Models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterf {

    public List<UserEntity> getAllUsers();
    public UserEntity getUserById(long idUser);
    public UserEntity CreatedUser(UserEntity userEntity);
    public UserEntity updateUser(Long userId, UserEntity userEntity);
    public String deleteUser(Long userId) ;

    UserEntity findByUsername(String currentPrincipalName);
}


