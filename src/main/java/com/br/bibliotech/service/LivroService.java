package com.br.bibliotech.service;

import com.br.bibliotech.api.livro.LivroRequest;
import com.br.bibliotech.api.livro.LivroResponse;
import com.br.bibliotech.model.livro.Livro;
import com.br.bibliotech.repository.LivroRepository;
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

        if (request.getImagemCapa() != null && !request.getImagemCapa().isEmpty()) {
            livro.setImagem(request.getImagemCapa().getBytes());
        }

        if (request.getPdf() != null && !request.getPdf().isEmpty()) {
            livro.setPdf(request.getPdf().getBytes());
        }

        livro.setHabilitado(Boolean.TRUE);

        return repository.save(livro);
    }

    // Listar todos os livros
    public List<LivroResponse> listarTodos() {
        return repository.findAll().stream()
                .map(LivroResponse::fromEntity)
                .toList();
    }


        // Buscar livro por ID
        public LivroResponse obterPorID(Long id) {
            Livro livro = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
            return LivroResponse.fromEntity(livro);
        }

    // Retorna a entidade Livro (para obter pdf e imagem)
    public Livro obterLivroPorID(Long id) {
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

        // Atualiza imagem se enviada
        if (request.getImagemCapa() != null && !request.getImagemCapa().isEmpty()) {
            livro.setImagem(request.getImagemCapa().getBytes());
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