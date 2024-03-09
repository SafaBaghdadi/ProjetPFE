package com.projetpfe.projetpfe.Service;

import com.projetpfe.projetpfe.Models.UserEntity;

import java.util.List;

public interface UserServiceInterf {

    public List<UserEntity> getAllUsers();
    public UserEntity getUserById(long idUser);
    public UserEntity CreatedUser(UserEntity userEntity);
    public UserEntity updateUser(Long userId, UserEntity userEntity);
    public String deleteUser(Long userId) ;
    }


