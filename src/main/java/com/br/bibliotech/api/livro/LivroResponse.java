package com.br.bibliotech.api.livro;

import java.time.LocalDate;
import java.util.Base64;

import com.br.bibliotech.model.livro.GeneroLivro;
import com.br.bibliotech.model.livro.Livro;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroResponse {

    private Long id;
    private String isbn;
    private String titulo;
    private LocalDate dataPublicacao;
    private GeneroLivro genero;
    private String nomeAutor;
    private String nacionalidadeAutor;

    private String imagemCapa; // base64 string da imagem
    private String pdf; // (opcional) pode ser URL ou base64 do pdf, conforme desejar

    // Converter entidade Livro em DTO
    public static LivroResponse fromEntity(Livro livro) {
        LivroResponse dto = new LivroResponse();

        dto.setId(livro.getId());
        dto.setIsbn(livro.getIsbn());
        dto.setTitulo(livro.getTitulo());
        dto.setDataPublicacao(livro.getDataPublicacao());
        dto.setGenero(livro.getGenero());
        dto.setNomeAutor(livro.getNomeAutor());
        dto.setNacionalidadeAutor(livro.getNacionalidadeAutor());

        if (livro.getImagem() != null) {
            String base64Img = Base64.getEncoder().encodeToString(livro.getImagem());
            dto.setImagemCapa("data:image/jpeg;base64," + base64Img);
        }

        // Se quiser enviar pdf como base64:
        // if (livro.getPdf() != null) {
        // String base64Pdf = Base64.getEncoder().encodeToString(livro.getPdf());
        // dto.setPdf("data:application/pdf;base64," + base64Pdf);
        // }

        return dto;
    }
}