package com.br.bibliotech.model.livro;

import java.time.LocalDate;

import org.hibernate.annotations.Where;

import com.br.bibliotech.util.entity.EntidadeAuditavel;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "livro")
@Where(clause = "habilitado = true")
public class Livro extends EntidadeAuditavel {

    @Column
    private String isbn;

    @Column
    private String titulo;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30, nullable = false)
    private GeneroLivro genero;

    @Column
    private String nomeAutor;

    @Column
    private String nacionalidadeAutor;

    @Lob
    @Column(name = "imagem_capa")
    private byte[] imagem;

    @Lob
    @Column(name = "pdf")
    private byte[] pdf;

    @Column(nullable = false)
    private Boolean habilitado = true; // campo importante para soft delete

}
