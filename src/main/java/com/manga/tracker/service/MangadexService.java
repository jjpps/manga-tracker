package com.manga.tracker.service;

import com.manga.tracker.client.MangadexClient;
import com.manga.tracker.client.response.MangaAttributes;
import com.manga.tracker.client.response.MangaDexData;
import com.manga.tracker.client.response.MangaDexResponse;
import com.manga.tracker.dto.MangaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MangadexService {
    private final MangadexClient mangadexClient;
    public MangaDto getManga(String url) {

        MangaDexResponse<MangaDexData<MangaAttributes>> mangaDexResponse = this.mangadexClient.getManga(url.split("/")[4]);
        return MangaDto.builder()
                .id(0L)
                .capLido(0L)
                //.nomeUltimoCapitulo(mangaDexResponse.getData().getAttributes().getLastChapter())
                .numeroUltimoCapitulo(mangaDexResponse.getData().getAttributes().getLastVolume().isEmpty()?0L:Long.parseLong(mangaDexResponse.getData().getAttributes().getLastVolume()))
                .quantidadeCapitulos(mangaDexResponse.getData().getAttributes().getLastChapter())
                .titulo(mangaDexResponse.getData().getAttributes().getTitle().get("en"))
                .build();
    }

}
