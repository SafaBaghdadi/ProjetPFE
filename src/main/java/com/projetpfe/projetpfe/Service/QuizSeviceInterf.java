package com.projetpfe.projetpfe.Service;

import com.projetpfe.projetpfe.Models.Quiz;

import java.util.List;

public interface QuizSeviceInterf {
    public Quiz createQuiz(Quiz quiz);
    public List<Quiz> getAllQuizzes();
    public Quiz getQuizById(Long idQuiz);
    public Quiz updateQuiz(Long idQuiz, Quiz quiz);
    public void deleteQuiz(Long idQuiz);
}
