package com.backend.portfolio.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.portfolio.DTO.bodyRequest.loginData;
import com.backend.portfolio.DTO.bodyResponse.apiResponse;
import com.backend.portfolio.DTO.bodyResponse.loginResponse;
import com.backend.portfolio.Models.Entities.PatientsEntity;
import com.backend.portfolio.Models.Entities.registerEntity;
import com.backend.portfolio.Services.loginService;
import com.backend.portfolio.Services.patientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
// @CrossOrigin(origins = { "http://127.0.0.1:3000",
// "https://fe-rekammedis.vercel.app/" })
public class login {

  @Autowired
  private loginService loginService;

  @Autowired
  private patientService patientService;

  // @Autowired
  // private ModelMapper modelMapper;

  @PostMapping
  public ResponseEntity<apiResponse<loginResponse>> loginRequest(
      @Valid @RequestBody loginData user) {
    apiResponse<loginResponse> response = new apiResponse<>();

    try {
      registerEntity loggedUser = loginService.loginUser(
          user.getUsername(),
          user.getPassword(),
          user.getUserId());
      List<PatientsEntity> patients = patientService.findAllWithSicknesses();

      loginResponse userLoginResponse = new loginResponse(
          loginService.generateLoginToken(loggedUser),
          patients);

      response.setMessage("Login Success");
      response.setPayload(userLoginResponse);
    } catch (RuntimeException e) {
      response.setMessage(e.getMessage());
      return ResponseEntity.status(401).body(response);
    }

    return ResponseEntity.ok(response);
  }
}
