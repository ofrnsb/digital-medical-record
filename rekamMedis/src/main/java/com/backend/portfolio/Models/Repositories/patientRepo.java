package com.backend.portfolio.Models.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.portfolio.Models.Entities.PatientsEntity;

public interface patientRepo extends JpaRepository<PatientsEntity, Long> {
  Optional<PatientsEntity> findByPatientName(String patientName);

  @Query("SELECT p FROM PatientsEntity p JOIN FETCH p.comingDate cd WHERE p.id = :patientId AND cd.id = :comingDateId")
  Optional<PatientsEntity> findPatientByPatientIdAndComingDateId(@Param("patientId") Long patientId,
      @Param("comingDateId") Long comingDateId);

  @Query("SELECT p FROM PatientsEntity p WHERE p.patientProgressStatus = 'first'")
  Optional<List<PatientsEntity>> getNewAndRevisitPatient();

  @Query("SELECT p FROM PatientsEntity p WHERE p.patientProgressStatus = 'second'")
  Optional<List<PatientsEntity>> getSecondStepPatient();

  @Query("SELECT p FROM PatientsEntity p WHERE p.patientProgressStatus = 'done'")
  Optional<List<PatientsEntity>> getThridStepPatient();

}
