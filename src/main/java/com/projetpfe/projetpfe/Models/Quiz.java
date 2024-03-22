package com.projetpfe.projetpfe.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idQuiz;
    private String title;
    private String Question;
    private String reponse1;
    private String reponse2;
    private String reponse3;

    @ManyToOne
    @JoinColumn(name = "Enseignant_id")

    private UserEntity Enseignant; // L'enseignant qui propose le quiz

    @OneToMany(mappedBy = "quiz")
    private List<QuizReponse> responses; // Les réponses des élèves au quiz

    // getters et setters
}