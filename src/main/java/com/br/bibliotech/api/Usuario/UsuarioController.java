package com.br.bibliotech.api.Usuario;

import com.br.bibliotech.model.usuario.Usuario;
import com.br.bibliotech.model.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Criar usuário
    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody UsuarioRequest request) {
        Usuario usuario = usuarioService.save(request);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    // Listar todos os usuários
    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obterPorID(@PathVariable Long id) {
        Usuario usuario = usuarioService.obterPorID(id);
        return ResponseEntity.ok(usuario);
    }

    // Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UsuarioRequest request) {
        usuarioService.update(id, request);
        return ResponseEntity.ok().build();
    }

    // Deletar usuário (desabilitar)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.ok().build();
    }
}