package com.br.bibliotech.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.br.bibliotech.controllers.docs.EmailControllerDocs;
import com.br.bibliotech.data.dto.request.EmailRequestDto;
import com.br.bibliotech.service.EmailService;

@RestController
@RequestMapping("/api/email/v1")
public class EmailController implements EmailControllerDocs {

  @Autowired
  private EmailService service;

  @PostMapping
  @Override
  public ResponseEntity<String> sendEmail(@RequestBody EmailRequestDto emailRequest) {
    service.sendSimpleEmail(emailRequest);
    return new ResponseEntity<>("e-Mail Enviado Com Sucesso!", HttpStatus.OK);
  }

  @Override
  public ResponseEntity<String> sendEmailWithAttachment(String emailRequestJson, MultipartFile multipartFile) {
    return null;
  }

}
