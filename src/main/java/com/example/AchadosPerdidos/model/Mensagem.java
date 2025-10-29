package com.example.AchadosPerdidos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "mensagens")
@Getter
@Setter
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMensagem;

    @ManyToOne
    @JoinColumn(name = "id_remetente", nullable = false)
    private Usuario remetente;

    @ManyToOne
    @JoinColumn(name = "id_destinatario", nullable = false)
    private Usuario destinatario;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String conteudo;

    private LocalDateTime dataEnvio = LocalDateTime.now();
}

