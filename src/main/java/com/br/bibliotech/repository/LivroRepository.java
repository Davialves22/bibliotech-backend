package com.br.bibliotech.repository;

import com.br.bibliotech.model.livro.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {

}