package com.projetpfe.projetpfe.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz_responses")
public class QuizReponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // autres attributs et annotations

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz; // Le quiz auquel cette réponse est associée

    @ManyToOne
    @JoinColumn(name = "eleve_id")
    private UserEntity eleve; // L'élève qui a répondu au quiz

    // getters et setters
}
