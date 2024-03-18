package com.projetpfe.projetpfe.Repository;

import com.projetpfe.projetpfe.Models.UserEntity;
import com.projetpfe.projetpfe.Models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<UserEntity> findByRole(UserRole role);


}
