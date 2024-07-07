package com.backend.portfolio.DTO.bodyRequest;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientUpdateDTO {

  private String sicknessName;
  private String note;
  private String diagnose;

  public PatientUpdateDTO(

      String sicknessName,
      String note,
      String diagnose) {

    this.sicknessName = sicknessName;

    this.note = note;
    this.diagnose = diagnose;
  }

  public PatientUpdateDTO() {
  }
}
