package com.backend.portfolio.Models.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.backend.portfolio.Models.Entities.doctorsEntity;

import jakarta.transaction.Transactional;

public interface doctorRepo extends JpaRepository<doctorsEntity, Long> {
  Optional<doctorsEntity> findByDoctorName(String doctorName);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM doctors_tbl WHERE id = ?1", nativeQuery = true)
  void removeById(Long id);

  @Modifying
  @Transactional
  @Query(value = "UPDATE doctors_tbl SET doctor_name = ?2 WHERE id = ?1", nativeQuery = true)
  void updateDoctorName(Long id, String doctorName);
}
