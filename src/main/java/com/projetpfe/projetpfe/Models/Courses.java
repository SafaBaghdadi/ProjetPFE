package com.projetpfe.projetpfe.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Courses {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idCourses;

        private String title;
        private String description;


        //enseignant
        @ManyToOne
        @JoinColumn(name = "enseignant_id")
        private UserEntity Enseignant;


        // Relation avec les utilisateurs (parents ou enfants) autorisés à consulter le cours
        @ManyToMany(mappedBy = "coursAutorises")


        private List<UserEntity> utilisateursAutorises;

        //image
        @OneToOne(mappedBy = "cours")

        private Image image;



}
