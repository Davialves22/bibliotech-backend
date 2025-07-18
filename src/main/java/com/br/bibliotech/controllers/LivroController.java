package com.br.bibliotech.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.bibliotech.api.livro.LivroRequest;
import com.br.bibliotech.api.livro.LivroResponse;
import com.br.bibliotech.controllers.docs.LivroControllerDocs;
import com.br.bibliotech.model.livro.Livro;
import com.br.bibliotech.service.LivroService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/livro/v1")
@CrossOrigin
@Tag(name = "Book", description = "Endpoints para Tratamento de Livros")
public class LivroController implements LivroControllerDocs {

    @Autowired
    private LivroService livroService;

    @PostMapping(consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
    })
    @Override
    public ResponseEntity<LivroResponse> save(@ModelAttribute LivroRequest request) throws IOException {
        Livro livro = livroService.save(request);
        return new ResponseEntity<>(LivroResponse.fromEntity(livro), HttpStatus.CREATED);
    }

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
    })
    @Override
    public List<LivroResponse> listarTodos() {
        return livroService.listarTodos();
    }

    @GetMapping("/pdf-existe/{id}")
    public ResponseEntity<Void> verificarSePdfExiste(@PathVariable Long id) {
        Livro livro = livroService.obterLivroPorID(id);

        if (livro.getPdf() != null) {
            return ResponseEntity.ok().build(); // Existe
        }

        return ResponseEntity.notFound().build(); // Não existe
    }

    @GetMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
    })
    @Override
    public ResponseEntity<LivroResponse> obterPorID(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.obterPorID(id)); // sem conversão extra
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/pdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> obterPdf(@PathVariable Long id) {
        Livro livro = livroService.obterLivroPorID(id);
        if (livro.getPdf() == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(livro.getPdf());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/imagem/{id}")
    public ResponseEntity<byte[]> obterImagem(@PathVariable Long id) {
        Livro livro = livroService.obterLivroPorID(id);
        if (livro.getImagem() == null)
            return ResponseEntity.notFound().build();

        byte[] imagem = livro.getImagem();
        MediaType mediaType = detectarMediaType(imagem);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(imagem);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResponseEntity<Void> update(@PathVariable Long id, @ModelAttribute LivroRequest request) throws IOException {
        livroService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        livroService.delete(id);
        return ResponseEntity.ok().build();
    }

    private MediaType detectarMediaType(byte[] imagem) {
        if (imagem.length >= 8 &&
                imagem[0] == (byte) 0x89 &&
                imagem[1] == (byte) 0x50 &&
                imagem[2] == (byte) 0x4E &&
                imagem[3] == (byte) 0x47) {
            return MediaType.IMAGE_PNG;
        }

        if (imagem.length >= 3 &&
                imagem[0] == (byte) 0xFF &&
                imagem[1] == (byte) 0xD8 &&
                imagem[2] == (byte) 0xFF) {
            return MediaType.IMAGE_JPEG;
        }

        return MediaType.APPLICATION_OCTET_STREAM;
    }
}