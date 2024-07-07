package com.backend.portfolio.DTO.bodyRequest;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class patientRegisterDTO {

  private String patientName;

  private Number patientAge;

  private String patientGender;

  private String patientAddress;

  private String patientPhoneNumber;

  private String patientStatus = "New";

  private String patientProgressStatus = "first";

  private LocalDate patientRegisteredDate = LocalDate.now();
}
