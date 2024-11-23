package com.manga.tracker.service;

import com.manga.tracker.client.MangadexClient;
import com.manga.tracker.client.response.MangaAttributes;
import com.manga.tracker.client.response.MangaDexData;
import com.manga.tracker.client.response.MangaDexResponse;
import com.manga.tracker.dto.MangaDto;
import com.manga.tracker.model.MangaModel;
import com.manga.tracker.repository.MangaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MangadexService {

    private MangadexClient mangadexClient;
    private MangaRepository mangaRepository;

    public MangaDto getManga(Long id) {
        MangaModel mangaModel = mangaRepository.findById(id).orElse(null);
        if (mangaModel != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Manga not found in DataBase");
        }
        MangaDexResponse<MangaDexData<MangaAttributes>> mangaDexResponse = mangadexClient.getManga(mangaModel.getUuid());
        return MangaDto.builder()
                .id(mangaModel.getId())
                .capLido(mangaModel.getCapLido())
                .nomeUltimoCapitulo(mangaDexResponse.getData().getAttributes().getLastChapter())
                //.numeroUltimoCapitulo(mangaResponse.getData().getAttributes().getLastVolume())
                .quantidadeCapitulos(mangaDexResponse.getData().getAttributes().getLastChapter())
                .titulo(mangaDexResponse.getData().getAttributes().getTitle())
                .build();
    }

}
