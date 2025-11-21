package com.example.AchadosPerdidos.service;

import com.example.AchadosPerdidos.model.Mensagem;
import com.example.AchadosPerdidos.repository.MensagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MensagemService {

    private final MensagemRepository mensagemRepository;

    @Autowired
    public MensagemService(MensagemRepository mensagemRepository) {
        this.mensagemRepository = mensagemRepository;
    }

    public Mensagem salvar(Mensagem mensagem) {
        return mensagemRepository.save(mensagem);
    }

    public List<Mensagem> buscarTodos() {
        return mensagemRepository.findAll();
    }

    public Optional<Mensagem> buscarPorId(Long id) {
        return mensagemRepository.findById(id);
    }

    public void deletar(Long id) {
        if (!mensagemRepository.existsById(id)) {
            throw new RuntimeException("Mensagem não encontrada com ID: " + id);
        }
        mensagemRepository.deleteById(id);
    }

    public List<Mensagem> buscarPorRemetente(Long idRemetente) {
        return mensagemRepository.findByRemetenteId(idRemetente);
    }

    public List<Mensagem> buscarPorDestinatario(Long idDestinatario) {
        return mensagemRepository.findByDestinatarioId(idDestinatario);
    }

    public List<Mensagem> buscarConversaEntre(Long id1, Long id2) {
        return mensagemRepository.buscarConversa(id1, id2);
    }
}
