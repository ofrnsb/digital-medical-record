package com.backend.portfolio.DTO.bodyResponse;

import com.backend.portfolio.Models.Entities.PatientsEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class patientAddDataResponse {

  private PatientsEntity patient;

  public patientAddDataResponse(PatientsEntity patient) {

    this.patient = patient;

  }

}
