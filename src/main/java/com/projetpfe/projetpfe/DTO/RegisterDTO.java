package com.projetpfe.projetpfe.DTO;

import com.projetpfe.projetpfe.Models.UserRole;
import lombok.Data;

@Data
public class RegisterDTO {
    private String firstname;

    private String lastname;

    private String username;

    private String email;

    private String password;

    private String matiere;

    private String  phoneNumber;

    private String  age;


    private UserRole role;
}
