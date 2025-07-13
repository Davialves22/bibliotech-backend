package com.br.bibliotech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.bibliotech.api.person.PersonRequest;
import com.br.bibliotech.model.usuario.Usuario;
import com.br.bibliotech.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class PersonService {

    @Autowired
    private UsuarioRepository repository;

    // Salvar usuário
    @Transactional
    public Usuario save(PersonRequest request) {
        Usuario usuario = request.build(); // Supondo que UsuarioRequest tenha método build()

        usuario.setHabilitado(Boolean.TRUE); // Como no Livro, habilitado = true

        return repository.save(usuario);
    }

    // Listar todos os usuários
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    // Buscar usuário por ID
    public Usuario obterPorID(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Atualizar usuário
    @Transactional
    public void update(Long id, PersonRequest request) {
        Usuario usuario = repository.findById(id)
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
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setHabilitado(Boolean.FALSE);
        repository.save(usuario);
    }
}