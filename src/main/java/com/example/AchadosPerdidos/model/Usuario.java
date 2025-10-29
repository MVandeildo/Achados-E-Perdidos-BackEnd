package com.example.AchadosPerdidos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false,unique = true,length = 100)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(length = 20)
    private String telefone;

    @Column(nullable = false,length = 20)
    private String tipoUsuario;

    private LocalDateTime dataCadastro = LocalDateTime.now();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Item> itens;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Notificacao> notificacoes;
    
}
