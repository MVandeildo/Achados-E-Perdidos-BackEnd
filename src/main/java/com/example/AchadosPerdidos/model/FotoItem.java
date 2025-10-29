package com.example.AchadosPerdidos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fotos_itens")
@Getter
@Setter
public class FotoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFoto;

    @Column(nullable = false)
    private String urlFoto;

    @ManyToOne
    @JoinColumn(name = "id_item", nullable = false)
    private Item item;
}
