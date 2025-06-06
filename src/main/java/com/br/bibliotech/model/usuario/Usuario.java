package com.br.bibliotech.model.usuario;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.SQLRestriction;

import com.br.bibliotech.util.entity.EntidadeAuditavel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // transforma numa classe exisitivel no jpa
@Table(name = "Usuario") // especifica que a classe sera convertida em tabela
@SQLRestriction("habilitado = true") // acresenta em todas as consultas uma clausula where: where habilidado = true

@Builder // forma de instanciar objetos da classe
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Usuario extends EntidadeAuditavel {

    @Column
    private String nome;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @Column
    private String cpf;

    @Column
    private String foneCelular;

    @Column
    private String email;

}