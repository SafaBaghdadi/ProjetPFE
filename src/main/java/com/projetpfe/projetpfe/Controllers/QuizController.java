package com.projetpfe.projetpfe.Controllers;

import com.projetpfe.projetpfe.Models.Quiz;
import com.projetpfe.projetpfe.Models.UserEntity;
import com.projetpfe.projetpfe.Repository.QuizRepository;
import com.projetpfe.projetpfe.Service.QuizServiceImpl;
import com.projetpfe.projetpfe.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/quizzes")
public class QuizController {
    public QuizController(QuizRepository quizRepository){
        this.quizRepository=quizRepository;

    }
    @Autowired
    private QuizServiceImpl quizServ;
    private QuizRepository quizRepository;
    @Autowired
    private UserServiceImpl userServ; // Assuming you have a UserService to retrieve the currently logged-in user

    // Endpoint pour créer un nouveau quiz
    @PostMapping("/create")
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Retrieve the user object using the username
        UserEntity user = userServ.findByUsername(currentPrincipalName);

        // Set the user as the Enseignant for the quiz
        quiz.setEnseignant(user);

        // Save the quiz
        return quizRepository.save(quiz);
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
