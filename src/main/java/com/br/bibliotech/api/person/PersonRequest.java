package com.br.bibliotech.api.person;

import java.time.LocalDate;

import com.br.bibliotech.model.person.Person;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonRequest {

    private String nome;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    private String cpf;
    private String foneCelular;
    private String email;
    private String senha; // <-- Adicionado
    private String role; // <-- Adicionado

    public Person build() {
        Person usuario = new Person();
        usuario.setNome(nome);
        usuario.setDataNascimento(dataNascimento);
        usuario.setCpf(cpf);
        usuario.setFoneCelular(foneCelular);
        usuario.setEmail(email);
        usuario.setSenha(senha); // <-- Adicionado
        usuario.setRole(role != null ? role : "ROLE_USER"); // <-- Adicionado
        return usuario;
    }
}
