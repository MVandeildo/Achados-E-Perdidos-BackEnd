package com.example.AchadosPerdidos.service;

import com.example.AchadosPerdidos.model.Notificacao;
import com.example.AchadosPerdidos.repository.NotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacaoService {

    @Autowired
    private final NotificacaoRepository notificacaoRepository;

    public NotificacaoService(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    public Notificacao salvar(Notificacao notificacao) {
        return notificacaoRepository.save(notificacao);
    }

    public List<Notificacao> buscarTodas() {
        return notificacaoRepository.findAll();
    }

    public Optional<Notificacao> buscarPorId(Long id) {
        return notificacaoRepository.findById(id);
    }

    public Notificacao marcarComoLida(Long id) {
        return notificacaoRepository.findById(id).map(notificacaoExistente -> {
            notificacaoExistente.setLida(true);
            return notificacaoRepository.save(notificacaoExistente);
        }).orElseThrow(() -> new RuntimeException("Notificação não encontrada com ID: " + id));
    }

    public void deletar(Long id) {
        if (!notificacaoRepository.existsById(id)) {
            throw new RuntimeException("Notificação não encontrada com ID: " + id);
        }
        notificacaoRepository.deleteById(id);
    }

    public List<Notificacao> buscarPorUsuario(Long usuarioId) {
        return notificacaoRepository.findByUsuarioId(usuarioId);
    }
}