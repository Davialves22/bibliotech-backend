package com.br.bibliotech.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.bibliotech.api.Usuario.UsuarioRequest;
import com.br.bibliotech.controllers.docs.UsuarioControllerDocs;
import com.br.bibliotech.model.usuario.Usuario;
import com.br.bibliotech.service.UsuarioService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/usuario/v1")
@CrossOrigin

@Tag(name = "People", description = "Endpoints para Tratamento de usuarios")
public class UsuarioController implements UsuarioControllerDocs {

    @Autowired
    private UsuarioService usuarioService;

    // Criar usuário
    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
    })

    @Override
    public ResponseEntity<Usuario> save(@RequestBody UsuarioRequest request) {
        Usuario usuario = usuarioService.save(request);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    // Listar todos os usuários
    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
    })
    @Override
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    // Buscar usuário por ID
    @GetMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            })
    @Override
    public ResponseEntity<Usuario> obterPorID(@PathVariable Long id) {
        Usuario usuario = usuarioService.obterPorID(id);
        return ResponseEntity.ok(usuario);
    }

    // Atualizar usuário
    @PutMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            })
    @Override
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UsuarioRequest request) {
        usuarioService.update(id, request);
        return ResponseEntity.ok().build();
    }

    // Deletar usuário (desabilitar)
    @DeleteMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.ok().build();
    }
}