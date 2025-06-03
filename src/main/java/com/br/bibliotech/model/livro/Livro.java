package com.br.bibliotech.model.livro;
import com.br.bibliotech.util.entity.EntidadeAuditavel;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "livro")
@SQLRestriction("habilitado = true") // (opcional) aplica filtro em todas as queries
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Livro extends EntidadeAuditavel {

    @Column
    private String isbn;

    @Column
    private String titulo;

    @Column
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30, nullable = false)
    private GeneroLivro genero;

    @Column(precision = 10, scale = 2)
    private BigDecimal preco;

    @Column
    private String nomeAutor;

    @Column
    private String nacionalidadeAutor;

    @Lob
    @Column(name = "imagem")
    private byte[] imagem;

    @Column(name = "imagem_url")
    private String imagemUrl;

    @Lob
    @Column(name = "pdf")
    private byte[] pdf;
}