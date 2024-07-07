// package com.backend.portfolio.Utils;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.lang.NonNull;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class WebConfig implements WebMvcConfigurer {

// @Override
// public void addCorsMappings(@NonNull CorsRegistry registry) {
// registry
// .addMapping("/**")
// .allowedOrigins("http://127.0.0.1:3000", "https://fe-rekammedis.vercel.app/")
// .allowedMethods("*")
// .allowedHeaders("*")
// .exposedHeaders("Authorization")
// .allowCredentials(true)
// .maxAge(3600);
// // .options("/moneymanagement/**"); // allow preflight requests for the
// // moneymanagement path
// }
// }
