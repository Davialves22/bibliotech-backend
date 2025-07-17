package com.br.bibliotech.api.person;

import java.time.LocalDate;

import com.br.bibliotech.model.person.Person;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonResponse {

  private Long id;
  private String nome;
  private LocalDate dataNascimento;
  private String cpf;
  private String foneCelular;
  private String email;

  // Método de conversão de entidade para DTO
  public static PersonResponse fromEntity(Person usuario) {
    PersonResponse dto = new PersonResponse();

    dto.setId(usuario.getId());
    dto.setNome(usuario.getNome());
    dto.setDataNascimento(usuario.getDataNascimento());
    dto.setCpf(usuario.getCpf());
    dto.setFoneCelular(usuario.getFoneCelular());
    dto.setEmail(usuario.getEmail());

    return dto;
  }
}
