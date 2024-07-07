package com.backend.portfolio.Models.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.portfolio.Models.Entities.registerEntity;

public interface registerRepo extends JpaRepository<registerEntity, Long> {
  @Query("SELECT p FROM registerEntity p WHERE p.username = :username ")
  public Optional<registerEntity> findByName(
    @Param("username") String username
  );
}
