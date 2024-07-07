package com.backend.portfolio.Models.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.backend.portfolio.Models.Entities.SicknessEntity;

import jakarta.transaction.Transactional;

public interface sicknessRepo extends JpaRepository<SicknessEntity, Long> {
  Optional<SicknessEntity> findBySicknessName(String sicknessName);

  @Query(value = "SELECT * FROM sickness_tbl WHERE sickness_name = ?1", nativeQuery = true)
  Optional<SicknessEntity> findSicknessByName(String sicknessName);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM sickness_tbl WHERE id = ?1", nativeQuery = true)
  void removeById(Integer id);

}
