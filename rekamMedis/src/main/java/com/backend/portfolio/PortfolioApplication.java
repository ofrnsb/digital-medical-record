package com.backend.portfolio;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.backend.portfolio.Utils.CustomErrorAttributes;

@SpringBootApplication
@EnableDiscoveryClient
public class PortfolioApplication {

  public static void main(String[] args) {
    SpringApplication.run(PortfolioApplication.class, args);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public DefaultErrorAttributes errorAttributes() {
    return new CustomErrorAttributes();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
