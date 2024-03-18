package com.projetpfe.projetpfe.Controllers;


import com.projetpfe.projetpfe.Models.Courses;
import com.projetpfe.projetpfe.Service.CoursesServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cours")
public class CoursesController {
        @Autowired
        private CoursesServicesImpl coursServ;

        @PostMapping("/addCours")
        public ResponseEntity<?> addCourses(@RequestBody Courses cours) {
            Courses nouveauCours = coursServ.addCourses(cours);
            return ResponseEntity.ok().body(nouveauCours);
        }

    }

