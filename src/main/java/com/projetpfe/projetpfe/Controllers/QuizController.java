package com.projetpfe.projetpfe.Controllers;

import com.projetpfe.projetpfe.Models.Quiz;
import com.projetpfe.projetpfe.Service.QuizServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    @Autowired
    private QuizServiceImpl quizServ;


    // Endpoint pour créer un nouveau quiz
    @PostMapping("/create")
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizServ.createQuiz(quiz);
    }


    // Endpoint pour récupérer tous les quizzes
    @GetMapping("/all")
    public List<Quiz> getAllQuizzes() {
        return quizServ.getAllQuizzes();
    }


    // Endpoint pour récupérer un quiz par son ID
    @GetMapping("/{idQuiz}")
    public Quiz getQuizById(@PathVariable Long id) {
        return quizServ.getQuizById(id);
    }


    // Endpoint pour mettre à jour un quiz
    @PutMapping("/update/{idQuiz}")
    public Quiz updateQuiz(@PathVariable Long idQuiz, @RequestBody Quiz quiz) {
        return quizServ.updateQuiz(idQuiz, quiz);
    }


    // Endpoint pour supprimer un quiz
    @DeleteMapping("/delete/{idQuiz}")
    public void deleteQuiz(@PathVariable Long idQuiz) {
        quizServ.deleteQuiz(idQuiz);
    }
}
