package com.br.bibliotech.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.mail")
public class EmailConfig {

  // Propriedades para envio e recebimento de email
  private String host;
  private int port;
  private String username;
  private String password;
  private String from;
  private boolean ssl;

}
