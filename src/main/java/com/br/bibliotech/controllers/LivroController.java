package com.br.bibliotech.api.livro;

import com.br.bibliotech.model.livro.Livro;
import com.br.bibliotech.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/livro")
@CrossOrigin
public class LivroController {

    @Autowired
    private LivroService livroService;

    // Criar livro com imagem e PDF
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Livro> save(@ModelAttribute LivroRequest request) throws IOException {
        Livro livro = livroService.save(request);
        return new ResponseEntity<>(livro, HttpStatus.CREATED);
    }

    // Listar todos os livros
    @GetMapping
    public List<Livro> listarTodos() {
        return livroService.listarTodos();
    }

    // Buscar livro por ID
    @GetMapping("/{id}")
    public Livro obterPorID(@PathVariable Long id) {
        return livroService.obterPorID(id);
    }

    // Buscar PDF do livro
    @GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> obterPdf(@PathVariable Long id) {
        Livro livro = livroService.obterPorID(id);
        if (livro.getPdf() == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(livro.getPdf());
    }

    // Buscar imagem do livro
    @GetMapping("/imagem/{id}")
    public ResponseEntity<byte[]> obterImagem(@PathVariable Long id) {
        Livro livro = livroService.obterPorID(id);

        if (livro.getImagem() == null)
            return ResponseEntity.notFound().build();

        byte[] imagem = livro.getImagem();

        // Detectar o tipo da imagem dinamicamente (PNG ou JPEG, por exemplo)
        MediaType mediaType = detectarMediaType(imagem);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(imagem);
    }

    // Atualizar livro (usando multipart)
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> update(@PathVariable Long id, @ModelAttribute LivroRequest request) throws IOException {
        livroService.update(id, request);
        return ResponseEntity.ok().build();
    }

    // Deletar livro
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

        // Padrão como fallback
        return MediaType.APPLICATION_OCTET_STREAM;
    }

}