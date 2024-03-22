package com.projetpfe.projetpfe.Service;


import com.projetpfe.projetpfe.Models.Quiz;
import com.projetpfe.projetpfe.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizSeviceInterf{


    @Autowired
    private QuizRepository quizRepository;

    // Méthode pour créer un nouveau quiz
    public Quiz createQuiz(Quiz quiz) {

        return quizRepository.save(quiz);
    }

    // Méthode pour récupérer tous les quizzes
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    // Méthode pour récupérer un quiz par son ID
    public Quiz getQuizById(Long idQuiz) {
        return quizRepository.findById(idQuiz)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable avec l'ID : " + idQuiz));
    }



    // Méthode pour mettre à jour un quiz
    public Quiz updateQuiz(Long idQuiz, Quiz quiz) {
        Quiz quizze = getQuizById(idQuiz);
        quizze.setTitle(quiz.getTitle());
        quizze.setQuestion(quiz.getQuestion());

        return quizRepository.save(quiz);
    }




    // Méthode pour supprimer un quiz
    public void deleteQuiz(Long idQuiz) {
        Quiz quiz = getQuizById(idQuiz);
        quizRepository.delete(quiz);
    }
}
