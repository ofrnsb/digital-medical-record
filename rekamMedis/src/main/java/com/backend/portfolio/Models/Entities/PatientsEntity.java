package com.backend.portfolio.Models.Entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity
@Table(name = "patients_tbl")
public class PatientsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "patient_name", nullable = false)
  private String patientName;

  @Column(name = "patient_age", nullable = false)
  private Number patientAge;

  @Column(name = "patien_gender", nullable = false)
  private String patientGender;

  @Column(name = "patien_address", nullable = false)
  private String patientAddress;

  @Column(name = "patien_phonenumber", nullable = false)
  private String patientPhoneNumber;

  @Column(name = "patient_registeredDate", nullable = false, updatable = false)
  private LocalDate patientRegisteredDate;

  @Column(name = "patien_status", nullable = false)
  private String patientStatus;

  @Column(name = "patien_progressStatus", nullable = false)
  private String patientProgressStatus;

  public String getPatientProgressStatus() {
    return patientProgressStatus;
  }

  public void setPatientProgressStatus(String patientProgressStatus) {
    this.patientProgressStatus = patientProgressStatus;
  }

  public String getPatientStatus() {
    return patientStatus;
  }

  public void setPatientStatus(String patientStatus) {
    this.patientStatus = patientStatus;
  }

  @ManyToMany
  @JoinTable(name = "patient_comingDate", joinColumns = @JoinColumn(name = "patient_id"), inverseJoinColumns = @JoinColumn(name = "coming_date_id"))
  @JsonManagedReference
  @OrderBy("id ASC")
  private List<PatientsComingDateEntity> comingDate;

  public List<PatientsComingDateEntity> getComingDate() {
    return comingDate;
  }

  public void setComingDate(List<PatientsComingDateEntity> comingDate) {
    this.comingDate = comingDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPatientName() {
    return patientName;
  }

  public void setPatientName(String patientName) {
    this.patientName = patientName;
  }

  public Number getPatientAge() {
    return patientAge;
  }

  public void setPatientAge(Number patientAge) {
    this.patientAge = patientAge;
  }

  public String getPatientGender() {
    return patientGender;
  }

  public void setPatientGender(String patientGender) {
    this.patientGender = patientGender;
  }

  public LocalDate getPatientRegisteredDate() {
    return patientRegisteredDate;
  }

  public void setPatientRegisteredDate(LocalDate patientRegisteredDate) {
    this.patientRegisteredDate = patientRegisteredDate;
  }

  public String getPatientAddress() {
    return patientAddress;
  }

  public void setPatientAddress(String patientAddress) {
    this.patientAddress = patientAddress;
  }

  public String getPatientPhoneNumber() {
    return patientPhoneNumber;
  }

  public void setPatientPhoneNumber(String patientPhoneNumber) {
    this.patientPhoneNumber = patientPhoneNumber;
  }

  @Override
  public String toString() {
    return "PatientsEntity{" +
        "id=" + id +
        ", comingDates=" + comingDate +
        '}';
  }

}
