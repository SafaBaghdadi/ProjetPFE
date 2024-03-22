package com.projetpfe.projetpfe.Models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
@Entity
@Table(name="profils")
public class Profil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProfil;
    private String firstname;
    private String lastname;
    private String matiere;
    private int age;
    private String phoneNumber;


    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;

}
