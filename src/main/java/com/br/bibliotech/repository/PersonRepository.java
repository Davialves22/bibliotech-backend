package com.br.bibliotech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.bibliotech.model.person.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByEmail(String email);
}
