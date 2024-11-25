package com.manga.tracker.service;
import com.manga.tracker.client.MangadexClient;
import com.manga.tracker.client.response.ChapterAttributes;
import com.manga.tracker.client.response.MangaAttributes;
import com.manga.tracker.client.response.MangaDexData;
import com.manga.tracker.client.response.MangaDexResponse;
import com.manga.tracker.dto.MangaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MangadexService {
    private final MangadexClient mangadexClient;
    public MangaDto getManga(String url) {
        try{
            MangaDexResponse<MangaDexData<MangaAttributes>> mangaAttributes = this.mangadexClient.getManga(url.split("/")[4]);
            MangaDexResponse<List<MangaDexData<ChapterAttributes>>> chapterAttributes = this.mangadexClient.getChapters(mangaAttributes.getData().getId(),1, "desc");
            return MangaDto.builder()
                    .id(0L)
                    .capLido(0L)
                    .nomeUltimoCapitulo(chapterAttributes.getData().getFirst().getAttributes().getTitle())
                    .numeroUltimoCapitulo(chapterAttributes.getData().getFirst().getAttributes().getChapter())
                    .quantidadeCapitulos(mangaAttributes.getData().getAttributes().getLastChapter())
                    .titulo(mangaAttributes.getData().getAttributes().getTitle().get("en"))
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
