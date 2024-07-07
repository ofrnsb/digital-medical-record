package com.backend.portfolio.Controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.portfolio.DTO.bodyRequest.request__updateDoctor;
import com.backend.portfolio.DTO.bodyResponse.apiResponse;
import com.backend.portfolio.Models.Entities.doctorsEntity;
import com.backend.portfolio.Models.Repositories.doctorRepo;
import com.backend.portfolio.Services.doctorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/doctor")
public class doctor {

  @Autowired
  private doctorService doctorService;

  @Autowired
  private doctorRepo doctorRepo;

  @Autowired
  private ModelMapper modelMapper;

  @GetMapping
  public ResponseEntity<apiResponse<List<doctorsEntity>>> getAllDoctors() {
    apiResponse<List<doctorsEntity>> response = new apiResponse<>();
    response.setMessage("Success");
    response.setPayload(doctorService.GetAllDoctor());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/add")
  public ResponseEntity<apiResponse<doctorsEntity>> addDoctor(
      @RequestBody @Valid request__updateDoctor newDoctor) {
    apiResponse<doctorsEntity> response = new apiResponse<>();
    response.setMessage("Success");

    response.setPayload(doctorService.AddDoctor(modelMapper.map(newDoctor, doctorsEntity.class)));
    return ResponseEntity.ok(response);
  }

  @PutMapping("/update")
  public ResponseEntity<apiResponse<String>> updateDoctor(@PathVariable Long doctorId,
      @RequestBody @Valid request__updateDoctor editedDoctor) {
    apiResponse<String> response = new apiResponse<>();

    doctorRepo.updateDoctorName(doctorId, editedDoctor.getDoctorName());

    response.setMessage("Success");
    response.setPayload(null);
    return ResponseEntity.ok(response);
  }
}
