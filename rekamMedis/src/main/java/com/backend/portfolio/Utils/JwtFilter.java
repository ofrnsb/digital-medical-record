package com.backend.portfolio.Utils;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  @Autowired
  private final JwtToken JwtToken;

  private final UserDetailsService UserDetailsService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    // Mengambil token dari header
    final String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      // Mengambil token
      filterChain.doFilter(request, response);
      return;
    }

    final String jwt = authorizationHeader.substring(7);

    try {
      if (JwtToken.isTokenExpired(jwt)) {
        filterChain.doFilter(request, response);
      }
    } catch (ExpiredJwtException e) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter(); // PrintWriter is an object used to write a string to the response
      String errMess = "{\"message\": \"token has expired\", \"payload\": false}"; // response body
      out.print(errMess); // to write the errMess to the response
      out.flush(); // to make sure that the response has been written to the response
      return;
    }

    final String username = JwtToken.extractUsername(jwt);

    // Cek apakah token ada atau tidak pada header

    // Cek apakah token udah expired atau belum

    if (username != null &&
        SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = UserDetailsService.loadUserByUsername(username);
      if (JwtToken.validateToken(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities());
        authToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    filterChain.doFilter(request, response);
  }
}
