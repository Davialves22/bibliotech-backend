/**
 * Classe responsável por configurar e armazenar os dados necessários para o envio de e-mails,
 * como destinatários, assunto, corpo da mensagem e anexos. 
 * Utiliza JavaMailSender do Spring para envio posterior do e-mail.
 * Os destinatários podem ser passados em formato de string separada por ponto e vírgula (;).
 * 
 * A classe utiliza métodos encadeáveis para facilitar a configuração do e-mail.
 */

package com.br.bibliotech.mail;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.br.bibliotech.config.EmailConfig;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailSender implements Serializable {

  private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

  private final JavaMailSender mailSender;
  private String to;
  private String subject;
  private String body;
  private ArrayList<InternetAddress> recipients = new ArrayList<>();
  private File attachment;

  /**
   * Construtor que inicializa o JavaMailSender para envio de e-mails.
   */
  public EmailSender(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  /**
   * Define os destinatários do e-mail a partir de uma string (com e-mails
   * separados por ponto e vírgula).
   * Também converte para uma lista de InternetAddress.
   */
  public EmailSender to(String to) {
    this.to = to;
    this.recipients = getRecipients(to);
    return this;
  }

  /**
   * Define o assunto do e-mail.
   */
  public EmailSender withSubject(String subject) {
    this.subject = subject;
    return this;
  }

  /**
   * Define o corpo da mensagem do e-mail (pode conter HTML).
   */
  public EmailSender withMessage(String body) {
    this.body = body;
    return this;
  }

  /**
   * Define um anexo para o e-mail a partir do caminho do arquivo.
   */
  public EmailSender attach(String fileDir) {
    this.attachment = new File(fileDir);
    return this;
  }

  /**
   * Envia o e-mail utilizando os dados configurados (remetente, destinatários,
   * assunto, mensagem e anexo).
   * Utiliza MimeMessageHelper para montar a mensagem.
   * Registra logs de sucesso ou erro e reseta o estado após envio.
   */
  public void send(EmailConfig config) {
    MimeMessage message = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(config.getUsername());
      helper.setTo(recipients.toArray(new InternetAddress[0]));
      helper.setSubject(subject);
      helper.setText(body, true);
      if (attachment != null) {
        helper.addAttachment(attachment.getName(), attachment);
      }
      mailSender.send(message);
      logger.info("E-mail enviado para {} com o assunto '{}'", to, subject);
      reset();
    } catch (MessagingException e) {
      logger.error("Erro ao enviar e-mail para: {}", to, e);
      throw new RuntimeException("Erro ao enviar e-mail", e);
    }
  }

  /**
   * Limpa os dados do e-mail após o envio (útil para reuso da instância).
   */
  private void reset() {
    this.to = null;
    this.subject = null;
    this.body = null;
    this.recipients = null;
    this.attachment = null;
  }

  /**
   * Converte uma string com e-mails separados por ponto e vírgula em uma lista de
   * InternetAddress.
   */
  private ArrayList<InternetAddress> getRecipients(String to) {
    String toWithoutSpaces = to.replaceAll("\\s", "");
    StringTokenizer tok = new StringTokenizer(toWithoutSpaces, ";");
    ArrayList<InternetAddress> recipientsList = new ArrayList<>();
    while (tok.hasMoreElements()) {
      try {
        recipientsList.add(new InternetAddress(tok.nextElement().toString()));
      } catch (AddressException e) {
        throw new RuntimeException("Endereço de e-mail inválido", e);
      }
    }
    return recipientsList;
  }
}
