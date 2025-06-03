package com.br.bibliotech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desativa CSRF (recomendado só para APIs)
                .cors(cors -> cors.disable()) // Ou use .cors(Customizer.withDefaults()) se quiser CORS habilitado
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Libera todas as rotas
                )
                .formLogin(login -> login.disable()) // Desativa login
                .httpBasic(basic -> basic.disable()); // Desativa autenticação básica

        return http.build();
    }
}