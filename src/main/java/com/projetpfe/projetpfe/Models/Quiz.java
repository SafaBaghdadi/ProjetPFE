package com.projetpfe.projetpfe.Models;

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
    private String description;


    @ManyToOne
    @JoinColumn(name = "Enseignant_id")
    private UserEntity Enseignant; // L'enseignant qui propose le quiz

    @OneToMany(mappedBy = "quiz")
    private List<QuizReponse> responses; // Les réponses des élèves au quiz

    // getters et setters
}