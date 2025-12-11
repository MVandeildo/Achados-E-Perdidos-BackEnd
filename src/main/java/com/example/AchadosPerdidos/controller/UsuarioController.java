package com.example.AchadosPerdidos.controller;

import com.example.AchadosPerdidos.model.Usuario;
import com.example.AchadosPerdidos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // POST: /api/usuarios
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.salvar(usuario);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }

    // GET: /api/usuarios
    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.buscarTodos();
    }

    // GET: /api/usuarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT: /api/usuarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetalhes) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizar(id, usuarioDetalhes);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: /api/usuarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET: /api/usuarios/email?email={valor}
    @GetMapping("/email")
    public ResponseEntity<Usuario> buscarPorEmail(@RequestParam String email) {
        return usuarioService.buscarPorEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}