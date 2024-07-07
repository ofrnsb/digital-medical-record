package com.backend.portfolio.DTO.bodyRequest;

public class request__updateDoctor {
  private String doctorName;

  public String getDoctorName() {
    return doctorName;
  }

  public void setDoctorName(String doctorName) {
    this.doctorName = doctorName;
  }

  public request__updateDoctor(String doctorName) {
    this.doctorName = doctorName;
  }

  public request__updateDoctor() {
  }

}
