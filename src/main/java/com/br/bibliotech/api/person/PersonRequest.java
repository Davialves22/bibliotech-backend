package com.br.bibliotech.api.person;

import java.time.LocalDate;

import com.br.bibliotech.model.usuario.Usuario;
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

    public Usuario build() {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setDataNascimento(dataNascimento);
        usuario.setCpf(cpf);
        usuario.setFoneCelular(foneCelular);
        usuario.setEmail(email);

        return usuario;
    }
}