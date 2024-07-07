package com.backend.portfolio.Models.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sickness_tbl")
@Setter
@Getter
public class SicknessEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  // @JsonIgnore
  private Long id;

  @Column(name = "sickness_name", nullable = false)
  private String sicknessName;

  // @ManyToMany(mappedBy = "sickness")
  // @JsonBackReference
  // private List<PatientsComingDateEntity> patientsComingDateEntity;
}
