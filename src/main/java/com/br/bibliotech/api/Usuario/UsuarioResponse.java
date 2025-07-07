package com.br.bibliotech.api.Usuario;

import java.time.LocalDate;

import com.br.bibliotech.model.usuario.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponse {

  private Long id;
  private String nome;
  private LocalDate dataNascimento;
  private String cpf;
  private String foneCelular;
  private String email;

  // Método de conversão de entidade para DTO
  public static UsuarioResponse fromEntity(Usuario usuario) {
    UsuarioResponse dto = new UsuarioResponse();

    dto.setId(usuario.getId());
    dto.setNome(usuario.getNome());
    dto.setDataNascimento(usuario.getDataNascimento());
    dto.setCpf(usuario.getCpf());
    dto.setFoneCelular(usuario.getFoneCelular());
    dto.setEmail(usuario.getEmail());

    return dto;
  }
}
