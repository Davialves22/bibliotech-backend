package com.br.bibliotech.api.livro;

import com.br.bibliotech.model.livro.GeneroLivro;
import com.br.bibliotech.model.livro.Livro;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LivroRequest {

    private String isbn;
    private String titulo;
    private LocalDate dataPublicacao;
    private GeneroLivro genero;
    private BigDecimal preco;

    private String nomeAutor;
    private String nacionalidadeAutor;

    private MultipartFile imagem;
    private String imagemUrl;
    private MultipartFile pdf;

    public Livro build() {
        Livro livro = new Livro();
        livro.setIsbn(isbn);
        livro.setTitulo(titulo);
        livro.setDataPublicacao(dataPublicacao);
        livro.setGenero(genero);
        livro.setPreco(preco);
        livro.setNomeAutor(nomeAutor);
        livro.setNacionalidadeAutor(nacionalidadeAutor);
        livro.setImagemUrl(imagemUrl);

        return livro;
    }
}