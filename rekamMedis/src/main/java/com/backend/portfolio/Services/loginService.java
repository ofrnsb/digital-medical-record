package com.backend.portfolio.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.backend.portfolio.Models.Entities.registerEntity;
import com.backend.portfolio.Models.Repositories.loginRepo;
import com.backend.portfolio.Utils.JwtToken;

@Service
public class loginService {

  @Autowired
  private loginRepo loginRepo;

  @Autowired
  private JwtToken jwtToken;

  @Autowired
  private AuthenticationManager AuthenticationManager;

  public registerEntity loginUser(
      String username,
      String password,
      int userId) {
    if (username.isBlank() || password.isBlank()) {
      throw new RuntimeException("Username or password cannot be blank");
    }

    Optional<registerEntity> userOptional = loginRepo.findByUsername(username);

    try {
      AuthenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));

      return userOptional.get();
    } catch (Exception e) {
      throw new RuntimeException("Invalid username or password");
    }
  }

  public String generateLoginToken(registerEntity user) {
    String jwt = jwtToken.generateToken(user);
    System.out.println(jwt);
    return jwt;
  }
}
