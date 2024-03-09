package com.projetpfe.projetpfe.Service;

import com.projetpfe.projetpfe.Models.Courses;
import com.projetpfe.projetpfe.Repository.CoursesRepository;

import java.util.List;

public interface CoursesServiceInterf {


        public Courses addCourses(Courses cours) ;

        public List<Courses> getAllCours() ;
    }


