package com.br.bibliotech.controllers;

import com.br.bibliotech.api.livro.LivroRequest;
import com.br.bibliotech.model.livro.Livro;
import com.br.bibliotech.service.LivroService;
import com.br.bibliotech.controllers.docs.LivroControllerDocs;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
    public ResponseEntity<Livro> save(@ModelAttribute LivroRequest request) throws IOException {
        Livro livro = livroService.save(request);
        return new ResponseEntity<>(livro, HttpStatus.CREATED);
    }

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
    })
    @Override
    public List<Livro> listarTodos() {
        return livroService.listarTodos();
    }

    @GetMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
    })
    @Override
    public ResponseEntity<Livro> obterPorID(@PathVariable Long id) {
        Livro livro = livroService.obterPorID(id);
        return ResponseEntity.ok(livro);
    }

    @GetMapping(value = "/pdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> obterPdf(@PathVariable Long id) {
        Livro livro = livroService.obterPorID(id);
        if (livro.getPdf() == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(livro.getPdf());
    }

    @GetMapping(value = "/imagem/{id}")
    public ResponseEntity<byte[]> obterImagem(@PathVariable Long id) {
        Livro livro = livroService.obterPorID(id);

        if (livro.getImagem() == null)
            return ResponseEntity.notFound().build();

        byte[] imagem = livro.getImagem();
        MediaType mediaType = detectarMediaType(imagem);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(imagem);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> update(@PathVariable Long id, @ModelAttribute LivroRequest request) throws IOException {
        livroService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
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