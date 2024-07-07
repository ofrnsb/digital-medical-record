package com.backend.portfolio.Models.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.backend.portfolio.Models.Entities.PatientsComingDateEntity;

import jakarta.transaction.Transactional;

public interface patientComingDateRepo
    extends JpaRepository<PatientsComingDateEntity, Long> {

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM coming_date_tbl WHERE id = ?1", nativeQuery = true)
  void removeById(Integer id);

}
