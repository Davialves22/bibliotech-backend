package com.br.bibliotech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.bibliotech.config.EmailConfig;
import com.br.bibliotech.mail.EmailSender;

@Service
public class EmailService {

  @Autowired
  private EmailSender emailSender;

  @Autowired
  private EmailConfig emailConfigs;

  // envia um email simples com campos de mensagem e destinatario
  public void sendSimpleEmail(String to, String subject, String body) {

    emailSender
        .to(to)
        .withSubject(subject)
        .withMessage(body)
        .send(emailConfigs);
  }

}
