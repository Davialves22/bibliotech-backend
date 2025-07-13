/**
 * Classe responsável por configurar e armazenar os dados necessários para o envio de e-mails,
 * como destinatários, assunto, corpo da mensagem e anexos. 
 * Utiliza JavaMailSender do Spring para envio posterior do e-mail.
 * Os destinatários podem ser passados em formato de string separada por ponto e vírgula (;).
 * 
 * A classe utiliza Lombok para reduzir código boilerplate, como getters, setters e encadeamento de métodos.
 */

package com.br.bibliotech.mail;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Component
@Getter
@Setter
@Accessors(chain = true)
public class EmailSender implements Serializable {

  private final JavaMailSender mailSender;

  private String to;
  private String subject;
  private String body;
  private ArrayList<InternetAddress> recipients = new ArrayList<>();
  private File attachment;

  public EmailSender(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public EmailSender to(String to) {
    this.to = to;
    this.recipients = getRecipients(to);
    return this;
  }

  // Recebe e-mails separados por ponto e vírgula e converte para uma lista de
  // InternetAddress
  private ArrayList<InternetAddress> getRecipients(String to) {
    String toWithoutSpaces = to.replaceAll("\\s", "");
    StringTokenizer tok = new StringTokenizer(toWithoutSpaces, ";");
    ArrayList<InternetAddress> recipientsList = new ArrayList<>();
    while (tok.hasMoreElements()) {
      try {
        recipientsList.add(new InternetAddress(tok.nextElement().toString()));
      } catch (AddressException e) {
        throw new RuntimeException(e);
      }
    }
    return recipientsList;
  }
}
