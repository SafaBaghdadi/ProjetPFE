package com.projetpfe.projetpfe.Service;


import com.projetpfe.projetpfe.Models.Courses;
import com.projetpfe.projetpfe.Models.Quiz;
import com.projetpfe.projetpfe.Repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CoursesServicesImpl {

        @Autowired
        private CoursesRepository coursesRepository;

        public Courses addCourses(Courses cours) {
            return coursesRepository.save(cours);
        }

        public List<Courses> getAllCourses() {
            return coursesRepository.findAll();
        }

    // Endpoint to delete a course by ID
    public void deleteCourse(Long idCourses) {
        Courses courses;
        courses = getCoursById(idCourses);
        coursesRepository.delete(courses);
    }

    public Courses getCoursById(Long idCourses) {
        return coursesRepository.findById(idCourses)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course introuvable avec l'ID : " + idCourses));
    }

    // Méthode pour mettre à jour un quiz
    public Courses updateCourses(Long idCourses, Courses courses) {
        Courses course = getCoursById(idCourses);
        course.setTitle(courses.getTitle());
        course.setDescription(courses.getDescription());
        course.setPrix(courses.getPrix());
        course.setDure(courses.getDure());

        return coursesRepository.save(course);
    }

}





