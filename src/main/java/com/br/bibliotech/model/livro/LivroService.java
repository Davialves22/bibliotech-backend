package com.br.bibliotech.model.livro;

import com.br.bibliotech.api.livro.LivroRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

    // Salvar livro
    @Transactional
    public Livro save(LivroRequest request) throws IOException {
        Livro livro = request.build();

        if (request.getImagem() != null && !request.getImagem().isEmpty()) {
            livro.setImagem(request.getImagem().getBytes());
        }

        if (request.getImagemUrl() != null && !request.getImagemUrl().isEmpty()) {
            livro.setImagemUrl(request.getImagemUrl());
        }

        if (request.getPdf() != null && !request.getPdf().isEmpty()) {
            livro.setPdf(request.getPdf().getBytes());
        }

        livro.setHabilitado(Boolean.TRUE);

        return repository.save(livro);
    }

    // Listar todos os livros
    public List<Livro> listarTodos() {
        return repository.findAll();
    }

    // Buscar livro por ID
    public Livro obterPorID(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    // Atualizar livro
    @Transactional
    public void update(Long id, LivroRequest request) throws IOException {
        Livro livro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        // Atualiza campos básicos
        livro.setTitulo(request.getTitulo());
        livro.setIsbn(request.getIsbn());
        livro.setGenero(request.getGenero());
        livro.setNomeAutor(request.getNomeAutor());
        livro.setNacionalidadeAutor(request.getNacionalidadeAutor());
        livro.setDataPublicacao(request.getDataPublicacao());
        livro.setPreco(request.getPreco());

        // Atualiza imagem se enviada
        if (request.getImagem() != null && !request.getImagem().isEmpty()) {
            livro.setImagem(request.getImagem().getBytes());
        }

        // Atualiza URL da imagem se enviada
        if (request.getImagemUrl() != null && !request.getImagemUrl().isEmpty()) {
            livro.setImagemUrl(request.getImagemUrl());
        }

        // Atualiza PDF se enviado
        if (request.getPdf() != null && !request.getPdf().isEmpty()) {
            livro.setPdf(request.getPdf().getBytes());
        }

        repository.save(livro);
    }

    // Deletar livro (lógica de desabilitação)
    @Transactional
    public void delete(Long id) {
        Livro livro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        livro.setHabilitado(Boolean.FALSE);
        repository.save(livro);
    }
}