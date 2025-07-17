package com.br.bibliotech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.bibliotech.data.dto.request.AuthRequest;
import com.br.bibliotech.data.dto.response.AuthResponse;
import com.br.bibliotech.model.person.Person;
import com.br.bibliotech.repository.PersonRepository;
import com.br.bibliotech.seguranca.JwtUtil;

@Service
public class AuthService {

  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private PersonRepository personRepo;

  @Autowired
  private PasswordEncoder encoder;

  public AuthResponse login(AuthRequest request) {
    Authentication auth = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.email(), request.senha()));

    Person person = (Person) auth.getPrincipal();
    String token = jwtUtil.generateToken(person);
    return new AuthResponse(token);
  }
}
