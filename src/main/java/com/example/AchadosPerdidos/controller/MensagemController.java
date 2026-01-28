package com.example.AchadosPerdidos.controller;

import com.example.AchadosPerdidos.model.Mensagem;
import com.example.AchadosPerdidos.model.Usuario;
import com.example.AchadosPerdidos.service.MensagemService;
import com.example.AchadosPerdidos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mensagens")
public class MensagemController {

    @Autowired
    private final MensagemService mensagemService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public MensagemController(MensagemService mensagemService) {
        this.mensagemService = mensagemService;
    }

    // POST: /api/mensagens/enviar
    // Envia mensagem extrayendo o usuário autenticado do JWT
    @PostMapping("/enviar")
    public ResponseEntity<?> enviarMensagem(
            @RequestBody MensagemRequest request) {
        try {
            // Obter usuário autenticado do token JWT
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String emailAutenticado = auth.getName();
            
            Optional<Usuario> remetenteOpt = usuarioRepository.findByEmail(emailAutenticado);
            if (remetenteOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Usuário não autenticado");
            }

            // Buscar destinatário pelo nome de usuário fornecido
            Optional<Usuario> destinatarioOpt = usuarioRepository.findByNome(request.nomeDestinatario());
            if (destinatarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Destinatário não encontrado");
            }

            // Criar e salvar mensagem
            Mensagem mensagem = new Mensagem();
            mensagem.setRemetente(remetenteOpt.get());
            mensagem.setDestinatario(destinatarioOpt.get());
            mensagem.setConteudo(request.conteudo());

            Mensagem novaMensagem = mensagemService.salvar(mensagem);
            return new ResponseEntity<>(novaMensagem, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao enviar mensagem: " + e.getMessage());
        }
    }

    // GET: /api/mensagens/minhas-mensagens
    // Busca mensagens enviadas e recebidas do usuário autenticado
    @GetMapping("/minhas-mensagens")
    public ResponseEntity<?> minhasMensagens() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String emailAutenticado = auth.getName();
            
            Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(emailAutenticado);
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Usuário não autenticado");
            }

            Long usuarioId = usuarioOpt.get().getId();
            List<Mensagem> enviadas = mensagemService.buscarPorRemetente(usuarioId);
            List<Mensagem> recebidas = mensagemService.buscarPorDestinatario(usuarioId);

            return ResponseEntity.ok(new MensagensUsuario(enviadas, recebidas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao buscar mensagens: " + e.getMessage());
        }
    }

    // GET: /api/mensagens/conversa/{usuarioId}
    // Busca conversa entre o usuário autenticado e outro usuário
    @GetMapping("/conversa/{usuarioId}")
    public ResponseEntity<?> buscarMeuHistoricoComUsuario(@PathVariable Long usuarioId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String emailAutenticado = auth.getName();
            
            Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(emailAutenticado);
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Usuário não autenticado");
            }

            Long meuId = usuarioOpt.get().getId();
            List<Mensagem> conversa = mensagemService.buscarConversaEntre(meuId, usuarioId);
            return ResponseEntity.ok(conversa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao buscar conversa: " + e.getMessage());
        }
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

    // DTO para requisição
    public record MensagemRequest(String nomeDestinatario, String conteudo) {
    }

    // DTO para resposta de mensagens do usuário
    public record MensagensUsuario(List<Mensagem> enviadas, List<Mensagem> recebidas) {
    }
}