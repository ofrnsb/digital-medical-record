package com.backend.portfolio.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.portfolio.Models.Entities.PatientsComingDateEntity;
import com.backend.portfolio.Models.Entities.PatientsEntity;
import com.backend.portfolio.Models.Entities.SicknessEntity;
import com.backend.portfolio.Models.Entities.doctorsEntity;
import com.backend.portfolio.Models.Repositories.doctorRepo;
import com.backend.portfolio.Models.Repositories.patientComingDateRepo;
import com.backend.portfolio.Models.Repositories.patientRepo;
import com.backend.portfolio.Models.Repositories.sicknessRepo;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.NonNull;

@Service
public class patientService {

  @Autowired
  private patientRepo patientRepo;

  @Autowired
  private sicknessRepo sicknessRepo;

  @Autowired
  private doctorRepo doctorRepo;

  @Autowired
  private patientComingDateRepo patientComingDateRepo;

  @Transactional
  public Optional<List<PatientsEntity>> getNewAndRevisitPatient() {
    return patientRepo.getNewAndRevisitPatient();
  }

  @Transactional
  public Optional<List<PatientsEntity>> getSecondStepPatient() {
    return patientRepo.getSecondStepPatient();
  }

  @Transactional
  public Optional<List<PatientsEntity>> getThridStepPatient() {
    return patientRepo.getThridStepPatient();
  }

  public Optional<PatientsEntity> findPatientById(@NonNull Long Id) {
    Boolean patient = patientRepo.findById(Id).isPresent();

    if (patient) {
      // return patientRepo.findPatientWithSicknessAndComingDate(Id);
      return patientRepo.findById(Id);
    } else {
      throw new RuntimeException("Patient not found");
    }
  }

  public List<PatientsEntity> findAllWithSicknesses() {
    return patientRepo.findAll();
  }

  @Transactional
  public PatientsEntity registerPatient(PatientsEntity patient) {

    boolean isExist = patientRepo
        .findByPatientName(patient.getPatientName())
        .isPresent();

    if (isExist) {
      PatientsEntity existingPatient = patientRepo
          .findByPatientName(patient.getPatientName()).get();
      existingPatient.setPatientStatus(
          "Revisit");
      existingPatient.setPatientProgressStatus("first");
      return existingPatient;
    } else {
      return patientRepo.save(patient);
    }
  }

  @Transactional

  public PatientsEntity updatePatientData(
      @NonNull Long patientId,
      @NonNull PatientsEntity editedPatient,
      @NonNull SicknessEntity addedSickness,
      @NonNull PatientsComingDateEntity patientsComingDate,
      Long patientComingDateId) {
    return patientRepo
        .findPatientByPatientIdAndComingDateId(patientId, patientComingDateId)
        .map(existingPatient -> {
          existingPatient.setPatientStatus(
              "Done");
          existingPatient.setPatientProgressStatus("done");

          existingPatient.getComingDate().get(0).setDiagnose(patientsComingDate.getDiagnose());
          existingPatient.getComingDate().get(0).setNote(patientsComingDate.getNote());
          existingPatient.getComingDate().get(0).getSickness().setSicknessName(addedSickness.getSicknessName());

          return patientRepo.save(existingPatient);
        }).orElseThrow(() -> new EntityNotFoundException("Patient with ID " + patientId + " not found"));

  }

  @Transactional
  public PatientsEntity addPatientData(
      @NonNull SicknessEntity sicknessEntity,
      @NonNull PatientsComingDateEntity patientsComingDate,
      @NonNull Long patientId,
      @NonNull doctorsEntity doctor) {

    PatientsEntity existingPatient = patientRepo
        .findById(patientId)
        .orElseThrow(() -> new EntityNotFoundException(
            "Patient with ID " + patientId + " not found"));

    doctorsEntity existingDoctor = doctorRepo
        .findByDoctorName(
            doctor.getDoctorName())
        .orElseGet(() -> {

          doctorRepo.save(doctor);
          return doctor;
        });

    patientsComingDate.setDoctor(existingDoctor);

    SicknessEntity existingSickness = sicknessRepo
        .findBySicknessName(sicknessEntity.getSicknessName()).orElseGet(() -> {
          sicknessRepo.save(sicknessEntity);
          return sicknessEntity;
        });

    patientsComingDate.setSickness(existingSickness);
    existingPatient.setPatientProgressStatus("second");
    patientComingDateRepo.save(patientsComingDate);
    existingPatient.getComingDate().add(patientsComingDate);
    return patientRepo.save(existingPatient);
  }
}
