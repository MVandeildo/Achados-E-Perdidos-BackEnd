package com.example.AchadosPerdidos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.AchadosPerdidos.model.Mensagem;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    
    List<Mensagem> findByRemetenteId(Long idRemetente);

    List<Mensagem> findByDestinatarioId(Long idDestinatario);

    @Query("SELECT m FROM Mensagem m WHERE " +
           "(m.remetente.id = :id1 AND m.destinatario.id = :id2) OR " +
           "(m.remetente.id = :id2 AND m.destinatario.id = :id1) " +
           "ORDER BY m.dataEnvio ASC")
    List<Mensagem> buscarConversa(Long id1, Long id2);

}
