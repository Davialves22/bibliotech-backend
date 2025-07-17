package com.br.bibliotech.seguranca;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.br.bibliotech.model.person.Person;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long expiration;

  // Gera token a partir do Authentication
  public String gerarToken(Authentication authentication) {
    Person usuario = (Person) authentication.getPrincipal();

    return Jwts.builder()
        .setSubject(usuario.getUsername())
        .claim("role", usuario.getAuthorities().stream().findFirst().get().getAuthority())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS512)
        .compact();
  }

  // Gera token a partir do Person diretamente
  public String generateToken(Person person) {
    return Jwts.builder()
        .setSubject(person.getUsername())
        .claim("role", person.getAuthorities().stream().findFirst().get().getAuthority())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS512)
        .compact();
  }

  // Extrai username do token (método usado pelo filtro)
  public String extractUsername(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  // Valida token
  public boolean isTokenValid(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  // Valida token e se pertence ao usuário certo
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && isTokenValid(token));
  }
}
