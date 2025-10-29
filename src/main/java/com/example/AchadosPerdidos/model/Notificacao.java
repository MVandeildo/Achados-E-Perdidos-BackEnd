package com.example.AchadosPerdidos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacoes")
@Getter
@Setter
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotificacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_item")
    private Item item;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    private LocalDateTime dataEnvio = LocalDateTime.now();

    private boolean lida = false;
}

