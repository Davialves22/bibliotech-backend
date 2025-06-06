package com.br.bibliotech.api.Usuario;

import com.br.bibliotech.model.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioRequest {

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