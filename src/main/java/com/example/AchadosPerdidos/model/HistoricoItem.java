package com.example.AchadosPerdidos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "historico_itens")
@Getter
@Setter
public class HistoricoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistorico;

    @OneToOne
    @JoinColumn(name = "id_item", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "id_usuario_responsavel")
    private Usuario usuarioResponsavel;

    private LocalDateTime dataResolucao = LocalDateTime.now();

    private String observacao;
}

