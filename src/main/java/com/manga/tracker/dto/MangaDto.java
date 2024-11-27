package com.manga.tracker.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MangaDto {
    public Long id;
    public String titulo;
    private String quantidadeCapitulos;
    private String numeroUltimoCapitulo;
    private String nomeUltimoCapitulo;
    private Long capLido;
    private String status;
    private String uuid;
    private String cover;
}
