package com.backend.portfolio.Models.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.portfolio.Models.Entities.registerEntity;

public interface loginRepo extends JpaRepository<registerEntity, Long> {
  Optional<registerEntity> findByUsernameAndPassword(
    String username,
    String password
  );

  Optional<registerEntity> findByUsername(String username);
}
