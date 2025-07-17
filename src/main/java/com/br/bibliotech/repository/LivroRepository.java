package com.br.bibliotech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.bibliotech.model.livro.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {

}