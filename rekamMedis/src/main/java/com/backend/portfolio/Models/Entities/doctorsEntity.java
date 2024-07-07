package com.backend.portfolio.Models.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "doctors_tbl")
@Getter
@Setter
public class doctorsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private Long id;

  @Column(name = "doctors_name", nullable = false)
  private String doctorName;

  // @ManyToOne(mappedBy = "doctor")
  // private PatientsComingDateEntity patientsComingDateEntity;
}
