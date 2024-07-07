package com.backend.portfolio.Utils;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.backend.portfolio.Models.Entities.registerEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtToken {

  public static final String Secret_Key = "77397A244326462948404D635166546A576E5A7234753778214125442A472D4B";

  // membuat key untuk token
  private Key signInKey() {
    byte[] decodedKey = Decoders.BASE64.decode(Secret_Key);
    return Keys.hmacShaKeyFor(decodedKey);
  }

  // mengenerate token
  public String generateToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails) {
    registerEntity registerEntity = (registerEntity) userDetails;
    return Jwts
        .builder()
        .addClaims(extraClaims)
        .setSubject(registerEntity.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 60 menit
        .signWith(signInKey())
        .compact();
    // perbedaan setClaims dengan addClaims
    // setClaims akan mengganti claims yang sudah ada dengan yang baru sedangkan
    // addClaims akan menambahkan claims baru tanpa menghapus claims yang sudah ada
  }

  // generate token dengan claims role dari user
  public String generateToken(UserDetails userDetails) {
    registerEntity registerEntity = (registerEntity) userDetails;
    Map<String, Object> claims = Map.of("role", registerEntity.getRole());
    System.out.println(claims);
    return generateToken(claims, userDetails);
  }

  // mengambil semua claims dari token
  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(signInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  // mengambil claims dari token
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  // perbedaan antara extractAllClaims dan extractClaim
  // extractAllClaims akan mengambil semua claims sedangkan extractClaim akan
  // mengambil claims yang kita inginkan

  // mengambil username dari token
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  // mengambil expiration date dari token
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  // mengecek apakah token sudah expired atau belum
  public boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  // mengecek apakah token valid atau tidak
  public boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
