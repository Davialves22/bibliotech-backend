package com.br.bibliotech.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "spring.mail")
@Getter
@Setter
public class EmailConfig {

  // Propriedades para envio e recebimento de email
  private String host;
  private int port;
  private String username;
  private String password;
  private String from;
  private boolean ssl;

}
