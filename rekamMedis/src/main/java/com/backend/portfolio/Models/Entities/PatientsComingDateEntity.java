package com.backend.portfolio.Models.Entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comingDate_tbl")
@Getter
@Setter
public class PatientsComingDateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "patient_comingDate", nullable = false, updatable = false)
  private LocalDate patientComingDate;

  @Column(name = "patient_note")
  private String note;

  @Column(name = "patient_diagnoses")
  private String diagnose;

  @Column(name = "patient_TD")
  private String bloodpressure;

  @Column(name = "patient_heartrate")
  private String HeartRate;

  @Column(name = "patient_respirationrate")
  private String respirationRate;

  @Column(name = "patient_temparature")
  private String temparature;

  @Column(name = "patient_height")
  private String height;

  @Column(name = "patient_weight")
  private String weight;

  @ManyToOne()
  private SicknessEntity sickness;

  @ManyToMany(mappedBy = "comingDate")
  @JsonBackReference
  private List<PatientsEntity> patients;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "doctor_id")
  private doctorsEntity doctor;

  @Override
  public String toString() {
    return "PatientsComingDateEntity{" +
        "id=" + id +
        '}';
  }

}
