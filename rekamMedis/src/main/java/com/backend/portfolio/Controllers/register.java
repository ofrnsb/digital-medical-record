package com.backend.portfolio.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.portfolio.DTO.bodyRequest.registerData;
import com.backend.portfolio.DTO.bodyResponse.apiResponse;
import com.backend.portfolio.Models.Entities.registerEntity;
import com.backend.portfolio.Services.registerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/register")
// @CrossOrigin(origins = { "https://fe-rekammedis.vercel.app/",
// "http://127.0.0.1:3000" }, allowCredentials = "true")
public class register {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private registerService registerService;

  @GetMapping
  public ResponseEntity<apiResponse<Iterable<registerEntity>>> getAllUsers() {
    apiResponse<Iterable<registerEntity>> response = new apiResponse<>();

    response.setMessage("Success");
    response.setPayload(registerService.findAll());

    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<apiResponse<String>> registerUser(
      @Valid @RequestBody registerData registerData) {
    apiResponse<String> response = new apiResponse<>();

    try {
      response.setPayload(null);
      response.setMessage(
          registerService.registerUser(
              modelMapper.map(registerData, registerEntity.class)));
    } catch (RuntimeException e) {
      response.setMessage(e.getMessage());
      return ResponseEntity.status(401).body(response);
    }

    return ResponseEntity.ok(response);
  }
}
