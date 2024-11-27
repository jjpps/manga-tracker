package com.manga.tracker.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity(name = "Manga")
@NoArgsConstructor
@AllArgsConstructor
public class MangaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, unique = true,name = "url")
    private String uuid;

    @Column(nullable = false)
    private Long quantidadeCapitulos;

    @Column(nullable = false)
    private String numeroUltimoCapitulo;

    @Column(nullable = false)
    private String nomeUltimoCapitulo;

    @Column(nullable = false)
    private Long capLido;
}
