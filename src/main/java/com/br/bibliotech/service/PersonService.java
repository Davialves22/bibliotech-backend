package com.br.bibliotech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.bibliotech.api.person.PersonRequest;
import com.br.bibliotech.model.person.Person;
import com.br.bibliotech.repository.PersonRepository;

import jakarta.transaction.Transactional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Person save(PersonRequest request) {
        Person usuario = request.build();
        usuario.setHabilitado(Boolean.TRUE);

        // Criptografa a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return repository.save(usuario);
    }

    // Listar todos os usuários
    public List<Person> listarTodos() {
        return repository.findAll();
    }

    // Buscar usuário por ID
    public Person obterPorID(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Atualizar usuário
    @Transactional
    public void update(Long id, PersonRequest request) {
        Person usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(request.getNome());
        usuario.setDataNascimento(request.getDataNascimento());
        usuario.setCpf(request.getCpf());
        usuario.setFoneCelular(request.getFoneCelular());
        usuario.setEmail(request.getEmail());

        repository.save(usuario);
    }

    // Deletar usuário (desabilitar)
    @Transactional
    public void delete(Long id) {
        Person usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setHabilitado(Boolean.FALSE);
        repository.save(usuario);
    }
}