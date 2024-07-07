package com.backend.portfolio.Controllers;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.portfolio.DTO.bodyRequest.PatientUpdateDTO;
import com.backend.portfolio.DTO.bodyRequest.patientRegisterDTO;
import com.backend.portfolio.DTO.bodyRequest.request__addDataPatientDTO;
import com.backend.portfolio.DTO.bodyResponse.apiResponse;
import com.backend.portfolio.DTO.bodyResponse.patientAddDataResponse;
import com.backend.portfolio.DTO.bodyResponse.patientRegisterResponse;
import com.backend.portfolio.Models.Entities.PatientsComingDateEntity;
import com.backend.portfolio.Models.Entities.PatientsEntity;
import com.backend.portfolio.Models.Entities.SicknessEntity;
import com.backend.portfolio.Models.Entities.doctorsEntity;
import com.backend.portfolio.Services.patientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/patient")
public class patient {

  @Autowired
  private patientService patientService;

  @Autowired
  private ModelMapper modelMapper;

  @GetMapping
  public ResponseEntity<apiResponse<Iterable<PatientsEntity>>> getAllPatients() {
    apiResponse<Iterable<PatientsEntity>> response = new apiResponse<>();
    response.setMessage("Success");
    response.setPayload(patientService.findAllWithSicknesses());
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{patientId}")
  public ResponseEntity<apiResponse<Optional<PatientsEntity>>> getPatientById(
      @PathVariable Long patientId) {
    apiResponse<Optional<PatientsEntity>> response = new apiResponse<>();
    if (patientId == null) {
      throw new RuntimeException("User ID cannot be null");
    }

    try {
      Optional<PatientsEntity> patientById = patientService.findPatientById(
          patientId);
      response.setMessage("Success");
      response.setPayload(patientById);
      return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
      response.setMessage(e.getMessage());
      return ResponseEntity.badRequest().body(response);
    }
  }

  @PostMapping("/register")
  public ResponseEntity<apiResponse<patientRegisterResponse>> registerPatient(
      @RequestBody @Valid patientRegisterDTO patientData) {
    apiResponse<patientRegisterResponse> response = new apiResponse<>();

    try {
      PatientsEntity patient = patientService.registerPatient(
          modelMapper.map(patientData, PatientsEntity.class));
      patientRegisterResponse patientResponse = new patientRegisterResponse(
          patient.getPatientName(),
          patient.getPatientAge(),
          patient.getPatientGender(),
          patient.getPatientAddress(),
          patient.getPatientPhoneNumber(),
          patient.getPatientRegisteredDate(),
          patient.getPatientStatus(),
          patient.getPatientProgressStatus());
      response.setPayload(patientResponse);

      response.setMessage("Success");

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      response.setMessage(e.getMessage());
      return ResponseEntity.badRequest().body(response);
    }
  }

  @GetMapping("/firstStep")
  public ResponseEntity<apiResponse<Optional<List<PatientsEntity>>>> getNewAndRevisitPatient() {
    apiResponse<Optional<List<PatientsEntity>>> response = new apiResponse<>();

    try {
      Optional<List<PatientsEntity>> patients = patientService.getNewAndRevisitPatient();

      response.setPayload(patients);

      response.setMessage("Success");

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      response.setMessage("Not Found");
      return ResponseEntity.badRequest().body(response);
    }
  }

  @GetMapping("/secondStep")
  public ResponseEntity<apiResponse<Optional<List<PatientsEntity>>>> getSecondStepPatient() {
    apiResponse<Optional<List<PatientsEntity>>> response = new apiResponse<>();

    try {
      Optional<List<PatientsEntity>> patients = patientService.getSecondStepPatient();

      response.setPayload(patients);

      response.setMessage("Success");

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      response.setMessage("Not Found");
      return ResponseEntity.badRequest().body(response);
    }
  }

  @GetMapping("/thirdStep")
  public ResponseEntity<apiResponse<Optional<List<PatientsEntity>>>> getThridStepPatient() {
    apiResponse<Optional<List<PatientsEntity>>> response = new apiResponse<>();

    try {
      Optional<List<PatientsEntity>> patients = patientService.getThridStepPatient();

      response.setPayload(patients);

      response.setMessage("Success");

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      response.setMessage("Not Found");
      return ResponseEntity.badRequest().body(response);
    }
  }

  @PostMapping("/addPatientData/{patientId}")
  public ResponseEntity<apiResponse<patientAddDataResponse>> addPatientData(
      @RequestBody @Valid request__addDataPatientDTO AddpatientData,
      @PathVariable Long patientId) {
    apiResponse<patientAddDataResponse> response = new apiResponse<>();

    PatientsEntity AddedPatientsData = patientService.addPatientData(
        modelMapper.map(AddpatientData, SicknessEntity.class),
        modelMapper.map(AddpatientData, PatientsComingDateEntity.class),
        patientId,
        modelMapper.map(AddpatientData, doctorsEntity.class));

    patientAddDataResponse patientResponse = new patientAddDataResponse(
        AddedPatientsData);

    try {
      response.setPayload(patientResponse);
      response.setMessage("Success");

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      response.setMessage(e.getMessage());
      return ResponseEntity.badRequest().body(response);
    }
  }

  @PutMapping("/update/{patientId}")
  public ResponseEntity<apiResponse<PatientsEntity>> updatePatientData(
      @PathVariable Long patientId,
      @RequestBody @Valid PatientUpdateDTO editedPatient,
      @RequestParam(required = false) Long comingDateId) {
    apiResponse<PatientsEntity> response = new apiResponse<>();

    try {
      response.setPayload(
          patientService.updatePatientData(
              patientId,
              modelMapper.map(editedPatient, PatientsEntity.class),
              modelMapper.map(editedPatient, SicknessEntity.class),
              modelMapper.map(editedPatient, PatientsComingDateEntity.class),
              comingDateId));
      response.setMessage("Success");

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      response.setMessage(e.getMessage());
      return ResponseEntity.badRequest().body(response);
    }
  }
}
