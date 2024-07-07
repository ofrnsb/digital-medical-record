package com.backend.portfolio.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.portfolio.Models.Entities.doctorsEntity;
import com.backend.portfolio.Models.Repositories.doctorRepo;

@Service
public class doctorService {

  @Autowired
  private doctorRepo doctorRepo;

  public List<doctorsEntity> GetAllDoctor() {
    return doctorRepo.findAll();
  }

  public doctorsEntity AddDoctor(
      doctorsEntity doctor) {
    return doctorRepo.save(doctor);
  }

  public doctorsEntity updateDoctor(doctorsEntity doctor) {
    return doctorRepo.save(doctor);
  }
}
