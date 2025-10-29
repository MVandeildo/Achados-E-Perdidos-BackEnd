package com.example.AchadosPerdidos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "itens")
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItem;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(length = 20)
    private String status = "Perdido"; // Perdido, Encontrado, Devolvido

    @Column(length = 100)
    private String localizacao;

    private LocalDateTime dataRegistro = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<FotoItem> fotos;

}

