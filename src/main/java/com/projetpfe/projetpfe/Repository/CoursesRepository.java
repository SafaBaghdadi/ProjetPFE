package com.projetpfe.projetpfe.Repository;

import com.projetpfe.projetpfe.Models.Courses;
import com.projetpfe.projetpfe.Models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoursesRepository  extends JpaRepository<Courses,Long> {


    Optional<Courses> findByIdCourses(Long idCourses);
}
