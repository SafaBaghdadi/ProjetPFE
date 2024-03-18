package com.projetpfe.projetpfe.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.projetpfe.projetpfe.Models.UserRole;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class RegisterDTO {
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;

    private String username;

    private String email;

    private String password;

    // Ignorer les attributs qui ne sont pas pertinents pour un type d'utilisateur donné
    @JsonIgnore
    private String matiere;
    @JsonIgnore
    private String phoneNumber;
    @JsonIgnore
    private Integer age;


    private UserRole role;


    public boolean isValidEmail() {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    // Méthodes getter et setter pour la propriété "age"
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

}
