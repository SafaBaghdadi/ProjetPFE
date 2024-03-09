package com.projetpfe.projetpfe.Service;


import com.projetpfe.projetpfe.Models.Courses;
import com.projetpfe.projetpfe.Repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursesServicesImpl {

        @Autowired
        private CoursesRepository coursesRepository;

        public Courses addCourses(Courses cours) {
            return coursesRepository.save(cours);
        }

        public List<Courses> getAllCours() {
            return coursesRepository.findAll();
        }
    }


