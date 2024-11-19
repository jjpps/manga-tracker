package com.manga.tracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Manga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, unique = true)
    private String url;

    @Column(nullable = false)
    private Integer quantidadeCapitulos;

    @Column(nullable = false)
    private Integer numeroUltimoCapitulo;

    @Column(nullable = false)
    private String nomeUltimoCapitulo;

    @Column(nullable = false)
    private Integer capLido;
}
