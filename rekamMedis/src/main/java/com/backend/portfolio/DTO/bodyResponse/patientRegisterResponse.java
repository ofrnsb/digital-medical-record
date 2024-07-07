package com.backend.portfolio.DTO.bodyResponse;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class patientRegisterResponse {

  private String patientName;
  private Number patientAge;
  private String patientGender;
  private String patientAddress;
  private String patientPhoneNumber;
  private LocalDate patientRegisteredDate;
  private String patientStatus;
  private String patientProgressStatus;
}
