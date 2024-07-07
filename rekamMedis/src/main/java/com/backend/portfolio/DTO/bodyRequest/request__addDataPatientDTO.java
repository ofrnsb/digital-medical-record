package com.backend.portfolio.DTO.bodyRequest;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class request__addDataPatientDTO {

  private LocalDate patientComingDate = LocalDate.now();
  private String bloodpressure;
  private String HeartRate;
  private String respirationRate;
  private String temparature;
  private String height;
  private String weight;
  private String sicknessName;
  private String doctorName;

}
