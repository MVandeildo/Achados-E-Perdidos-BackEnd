package com.example.AchadosPerdidos.controller;

import com.example.AchadosPerdidos.model.Mensagem;
import com.example.AchadosPerdidos.service.MensagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensagens")
public class MensagemController {

    private final MensagemService mensagemService;

    @Autowired
    public MensagemController(MensagemService mensagemService) {
        this.mensagemService = mensagemService;
    }

    // POST: /api/mensagens
    @PostMapping
    public ResponseEntity<Mensagem> criarMensagem(@RequestBody Mensagem mensagem) {
        Mensagem novaMensagem = mensagemService.salvar(mensagem);
        return new ResponseEntity<>(novaMensagem, HttpStatus.CREATED);
    }

    // GET: /api/mensagens
    @GetMapping
    public List<Mensagem> listarTodas() {
        return mensagemService.buscarTodos();
    }

    // GET: /api/mensagens/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Mensagem> buscarPorId(@PathVariable Long id) {
        return mensagemService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: /api/mensagens/conversa?id1=1&id2=2
    @GetMapping("/conversa")
    public List<Mensagem> buscarConversa(@RequestParam Long id1, @RequestParam Long id2) {
        return mensagemService.buscarConversaEntre(id1, id2);
    }

    // DELETE: /api/mensagens/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMensagem(@PathVariable Long id) {
        try {
            mensagemService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}