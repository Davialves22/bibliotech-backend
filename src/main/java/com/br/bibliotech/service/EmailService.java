package com.br.bibliotech.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.br.bibliotech.config.EmailConfig;
import com.br.bibliotech.data.dto.request.EmailRequestDto;
import com.br.bibliotech.mail.EmailSender;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Serviço responsável por orquestrar o envio de e-mails simples e com anexos.
 * Utiliza a classe {@link EmailSender} para configurar e disparar os e-mails.
 * 
 * Esta classe é gerenciada pelo Spring via a anotação @Service.
 */
@Service
public class EmailService {

  @Autowired
  private EmailSender emailSender;

  @Autowired
  private EmailConfig emailConfigs;

  /**
   * Envia um e-mail simples (sem anexo) para o destinatário especificado.
   * 
   * @param emailRequest Objeto contendo o destinatário, assunto e corpo do
   *                     e-mail.
   * 
   *                     Obs: atualmente está utilizando o assunto como corpo da
   *                     mensagem — considerar ajuste se necessário.
   */
  public void sendSimpleEmail(EmailRequestDto emailRequest) {
    emailSender
        .to(emailRequest.getTo())
        .withSubject(emailRequest.getSubject())
        .withMessage(emailRequest.getSubject()) // Pode ser alterado para emailRequest.getBody()
        .send(emailConfigs);
  }

  /**
   * Envia um e-mail com anexo a partir de um JSON com dados do e-mail e um
   * arquivo.
   * 
   * O JSON deve representar um objeto {@link EmailRequestDto}.
   * O anexo é temporariamente salvo em disco para ser enviado.
   * 
   * @param emailRequestJson JSON com os dados do e-mail (to, subject, body).
   * @param attachment       Arquivo a ser enviado como anexo.
   * 
   * @throws RuntimeException caso ocorra erro na leitura do JSON ou no
   *                          processamento do anexo.
   */
  public void sendEmailWithAttachment(String emailRequestJson, MultipartFile attachment) {
    File tempFile = null;

    try {
      // Converte o JSON recebido para um objeto EmailRequestDto
      EmailRequestDto emailRequest = new ObjectMapper().readValue(emailRequestJson, EmailRequestDto.class);

      // Cria um arquivo temporário para armazenar o anexo
      tempFile = File.createTempFile("Anexo", attachment.getOriginalFilename());
      attachment.transferTo(tempFile);

      // Configura o e-mail com anexo usando fluent interface
      emailSender
          .to(emailRequest.getTo())
          .withSubject(emailRequest.getSubject())
          .withMessage(emailRequest.getSubject()) // Pode ser alterado para emailRequest.getBody()
          .attach(tempFile.getAbsolutePath())
          .send(emailConfigs);

    } catch (JsonMappingException e) {
      // Trata erro de conversão do JSON
      throw new RuntimeException("Erro ao processar o email request JSON", e);
    } catch (IOException e) {
      // Trata erro de manipulação de arquivos
      throw new RuntimeException("Erro ao processar o Anexo!", e);
    } finally {
      // Garante que o arquivo temporário seja excluído
      if (tempFile != null && tempFile.exists())
        tempFile.delete();
    }
  }
}
