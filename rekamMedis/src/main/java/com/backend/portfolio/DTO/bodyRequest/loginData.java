package com.backend.portfolio.DTO.bodyRequest;

public class loginData {

  String username;
  String password;
  int userId;

  public loginData(int userId, String username, String password) {
    this.userId = userId;
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }
}
