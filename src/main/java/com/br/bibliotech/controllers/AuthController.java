package com.br.bibliotech.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.bibliotech.data.dto.request.LoginRequest;
import com.br.bibliotech.data.dto.response.TokenResponse;
import com.br.bibliotech.seguranca.JwtUtil;

@RestController
@RequestMapping("/login")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtil jwtUtil;

  @PostMapping
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha()));

    String token = jwtUtil.gerarToken(authentication);

    return ResponseEntity.ok(new TokenResponse(token));
  }
}
