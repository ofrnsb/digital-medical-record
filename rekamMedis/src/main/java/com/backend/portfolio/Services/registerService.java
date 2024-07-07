package com.backend.portfolio.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.portfolio.Models.Entities.registerEntity;
import com.backend.portfolio.Models.Repositories.registerRepo;

@Service
public class registerService {

  @Autowired
  private registerRepo registerRepo;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public List<registerEntity> findAll() {
    return registerRepo.findAll();
  }

  public registerEntity findUserById(@NonNull Long id) {
    Optional<registerEntity> product = registerRepo.findById(id);
    if (product.isEmpty()) {
      return null;
    }
    return product.get();
  }

  public String registerUser(registerEntity user) {
    if (user.getUsername().isBlank() || user.getPassword().isBlank()) {
      throw new RuntimeException("Username or password cannot be blank");
    } else if (user.getUsername().length() < 6) {
      throw new RuntimeException("Username must be at least 6 characters");
    } else if (user.getPassword().length() < 8) {
      throw new RuntimeException("Password must be at least 8 characters");
    } else if (!user.getPassword().matches("^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9]).+$")) {
      throw new RuntimeException(
          "Password must contain at least one uppercase letter, one special character, and one number");
    }

    boolean isExist = registerRepo.findByName(user.getUsername()).isPresent();

    if (isExist) {
      throw new RuntimeException(
          "The account is already registered".formatted());
    } else {
      user.setUsername(user.getUsername());
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      user.setRole(user.getRole());
      registerRepo.save(user);

      return "Successfully registered";
    }
  }
}
