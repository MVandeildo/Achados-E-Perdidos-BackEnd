package com.example.AchadosPerdidos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AchadosPerdidos.model.Notificacao;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

     List<Notificacao> findByUsuarioId(Long usuarioId);

    List<Notificacao> findByItemId(Long itemId);

    List<Notificacao> findByMensagemContainingIgnoreCase(String texto);
    
}
