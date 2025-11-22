package com.example.AchadosPerdidos.controller;

import com.example.AchadosPerdidos.model.Notificacao;
import com.example.AchadosPerdidos.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    @Autowired
    public NotificacaoController(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    // POST: /api/notificacoes
    @PostMapping
    public ResponseEntity<Notificacao> criarNotificacao(@RequestBody Notificacao notificacao) {
        Notificacao novaNotificacao = notificacaoService.salvar(notificacao);
        return new ResponseEntity<>(novaNotificacao, HttpStatus.CREATED);
    }

    // GET: /api/notificacoes
    @GetMapping
    public List<Notificacao> listarTodas() {
        return notificacaoService.buscarTodas();
    }

    // GET: /api/notificacoes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Notificacao> buscarPorId(@PathVariable Long id) {
        return notificacaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT: /api/notificacoes/{id}/lida
    @PutMapping("/{id}/lida")
    public ResponseEntity<Notificacao> marcarComoLida(@PathVariable Long id) {
        try {
            Notificacao notificacaoAtualizada = notificacaoService.marcarComoLida(id);
            return ResponseEntity.ok(notificacaoAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET: /api/notificacoes/usuario/{usuarioId}
    @GetMapping("/usuario/{usuarioId}")
    public List<Notificacao> buscarPorUsuario(@PathVariable Long usuarioId) {
        return notificacaoService.buscarPorUsuario(usuarioId);
    }

    // DELETE: /api/notificacoes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarNotificacao(@PathVariable Long id) {
        try {
            notificacaoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}