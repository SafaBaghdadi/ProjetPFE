package com.projetpfe.projetpfe.Repository;

import com.projetpfe.projetpfe.Models.Role;
import com.projetpfe.projetpfe.Models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {


    Optional<Role> findByName(UserRole Role);

}