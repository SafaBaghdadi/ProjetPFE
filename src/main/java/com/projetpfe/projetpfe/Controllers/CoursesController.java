package com.projetpfe.projetpfe.Controllers;


import com.projetpfe.projetpfe.Models.Courses;
import com.projetpfe.projetpfe.Models.Quiz;
import com.projetpfe.projetpfe.Models.UserEntity;
import com.projetpfe.projetpfe.Repository.CoursesRepository;
import com.projetpfe.projetpfe.Repository.QuizRepository;
import com.projetpfe.projetpfe.Service.CoursesServicesImpl;
import com.projetpfe.projetpfe.Service.QuizServiceImpl;
import com.projetpfe.projetpfe.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cours")
public class CoursesController {
    private final CoursesRepository coursesRepository;
    @Autowired
        private CoursesServicesImpl coursServ;
    @Autowired
    private UserServiceImpl userServ; // Assuming you have a UserService to retrieve the currently logged-in user

    public CoursesController(CoursesRepository coursesRepository){
        this.coursesRepository=coursesRepository;

    }
    @PostMapping("/addCours")
    public Courses addCourses(@RequestBody Courses cours) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Retrieve the user object using the username
        UserEntity user = userServ.findByUsername(currentPrincipalName);

        // Set the user as the Enseignant for the quiz
        cours.setEnseignant(user);

        // Save the quiz
        return coursesRepository.save(cours);
    }

    // Endpoint to get all courses
    @GetMapping("/getAllCourses")
    public List<Courses> getAllCourses() {
        return coursServ.getAllCourses();
    }


    @GetMapping("/getCoursById/{idCourses}")
   public Courses getCoursById(@PathVariable Long idCourses) {
        return coursServ.getCoursById(idCourses);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteCourse(@PathVariable Long idCourses) {
        coursServ.deleteCourse(idCourses);
    }

    // Endpoint to update a course
    @PutMapping("/update/{id}")
    public Courses updateCourse(@PathVariable Long id, @RequestBody Courses updatedCourse) {
        return coursServ.updateCourses(id, updatedCourse);
    }
    }

