package com.projetpfe.projetpfe.Repository;

import com.projetpfe.projetpfe.Models.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepository  extends JpaRepository<Courses,Long> {


}
