package com.backend.portfolio.DTO.bodyResponse;

import java.util.List;

import com.backend.portfolio.Models.Entities.PatientsEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class loginResponse {

  private String jwt;
  private List<PatientsEntity> patient;

  public loginResponse(String jwt, List<PatientsEntity> patient) {
    this.jwt = jwt;
    this.patient = patient;
  }
}
