package com.manga.tracker.service;
import com.manga.tracker.client.MangadexClient;
import com.manga.tracker.client.response.ChapterAttributes;
import com.manga.tracker.client.response.MangaAttributes;
import com.manga.tracker.client.response.MangaDexData;
import com.manga.tracker.client.response.MangaDexResponse;
import com.manga.tracker.dto.MangaRequest;
import com.manga.tracker.dto.MangaDto;
import com.manga.tracker.model.MangaModel;
import com.manga.tracker.repository.MangaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
@Slf4j
@Service
@RequiredArgsConstructor
public class MangadexService {
    private final MangadexClient mangadexClient;
    private final MangaRepository repository;
    public MangaDto getManga(String url) {
        try{
            MangaDexResponse<MangaDexData<MangaAttributes>> mangaAttributes = this.mangadexClient.getManga(url.split("/")[4]);
            MangaDexResponse<List<MangaDexData<ChapterAttributes>>> chapterAttributes = this.mangadexClient.getChapters(mangaAttributes.getData().getId(),1, "desc","pt-br");
            return MangaDto.builder()
                    .id(0L)
                    .capLido(0L)
                    .nomeUltimoCapitulo(Objects.isNull(chapterAttributes.getData().getFirst().getAttributes().getTitle())? MessageFormat.format("Cap: {0}",chapterAttributes.getData().getFirst().getAttributes().getChapter()):chapterAttributes.getData().getFirst().getAttributes().getTitle())
                    .numeroUltimoCapitulo(chapterAttributes.getData().getFirst().getAttributes().getChapter())
                    .quantidadeCapitulos(mangaAttributes.getData().getAttributes().getLastChapter().isEmpty()?chapterAttributes.getData().getFirst().getAttributes().getChapter():mangaAttributes.getData().getAttributes().getLastChapter())
                    .titulo(mangaAttributes.getData().getAttributes().getTitle().get("en"))
                    .status(mangaAttributes.getData().getAttributes().getStatus())
                    .uuid(mangaAttributes.getData().getId())
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    public MangaDto getMangaByUUID(String uuid) {
        try{
            MangaDexResponse<MangaDexData<MangaAttributes>> mangaAttributes = this.mangadexClient.getManga(uuid);
            MangaDexResponse<List<MangaDexData<ChapterAttributes>>> chapterAttributes = this.mangadexClient.getChapters(mangaAttributes.getData().getId(),1, "desc","pt-br");
            return MangaDto.builder()
                    .id(0L)
                    .capLido(0L)
                    .nomeUltimoCapitulo(Objects.isNull(chapterAttributes.getData().getFirst().getAttributes().getTitle())? MessageFormat.format("Cap: {0}",chapterAttributes.getData().getFirst().getAttributes().getChapter()):chapterAttributes.getData().getFirst().getAttributes().getTitle())
                    .numeroUltimoCapitulo(chapterAttributes.getData().getFirst().getAttributes().getChapter())
                    .quantidadeCapitulos(mangaAttributes.getData().getAttributes().getLastChapter().isEmpty()?chapterAttributes.getData().getFirst().getAttributes().getChapter():mangaAttributes.getData().getAttributes().getLastChapter())
                    .titulo(mangaAttributes.getData().getAttributes().getTitle().get("en"))
                    .status(mangaAttributes.getData().getAttributes().getStatus())
                    .uuid(mangaAttributes.getData().getId())
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    public MangaDto saveManga(MangaRequest mangaRequest){
        MangaDexResponse<MangaDexData<MangaAttributes>> mangaAttributes = this.mangadexClient.getManga(mangaRequest.getId());

        MangaDexResponse<List<MangaDexData<ChapterAttributes>>> chapterAttributes = this.mangadexClient.getChapters(mangaAttributes.getData().getId(),1, "desc","pt-br");
        MangaModel model = this.repository.findByUuid(mangaRequest.getId());
        if(!Objects.isNull(model)){
            return MangaDto.builder()
                    .id(model.getId())
                    .capLido(model.getCapLido())
                    .nomeUltimoCapitulo(Objects.isNull(chapterAttributes.getData().getFirst().getAttributes().getTitle())? MessageFormat.format("Cap: {0}",chapterAttributes.getData().getFirst().getAttributes().getChapter()):chapterAttributes.getData().getFirst().getAttributes().getTitle())
                    .numeroUltimoCapitulo(chapterAttributes.getData().getFirst().getAttributes().getChapter())
                    .quantidadeCapitulos(mangaAttributes.getData().getAttributes().getLastChapter().isEmpty()?chapterAttributes.getData().getFirst().getAttributes().getChapter():mangaAttributes.getData().getAttributes().getLastChapter())
                    .titulo(mangaAttributes.getData().getAttributes().getTitle().get("en"))
                    .status(mangaAttributes.getData().getAttributes().getStatus())
                    .uuid(mangaAttributes.getData().getId())
                    .build();
        }
        model = MangaModel.builder()
                .titulo(mangaAttributes.getData().getAttributes().getTitle().get("en"))
                .uuid(mangaAttributes.getData().getId())
                .capLido(mangaRequest.getCapLido())
                .nomeUltimoCapitulo(Objects.isNull(chapterAttributes.getData().getFirst().getAttributes().getTitle())? MessageFormat.format("Cap: {0}",chapterAttributes.getData().getFirst().getAttributes().getChapter()):chapterAttributes.getData().getFirst().getAttributes().getTitle())
                .numeroUltimoCapitulo(chapterAttributes.getData().getFirst().getAttributes().getChapter())
                .quantidadeCapitulos(Long.parseLong(mangaAttributes.getData().getAttributes().getLastChapter().isEmpty()?chapterAttributes.getData().getFirst().getAttributes().getChapter():mangaAttributes.getData().getAttributes().getLastChapter()))
                .build();
        repository.save(model);
        return MangaDto.builder()
                .id(model.getId())
                .capLido(model.getCapLido())
                .nomeUltimoCapitulo(Objects.isNull(chapterAttributes.getData().getFirst().getAttributes().getTitle())? MessageFormat.format("Cap: {0}",chapterAttributes.getData().getFirst().getAttributes().getChapter()):chapterAttributes.getData().getFirst().getAttributes().getTitle())
                .numeroUltimoCapitulo(chapterAttributes.getData().getFirst().getAttributes().getChapter())
                .quantidadeCapitulos(mangaAttributes.getData().getAttributes().getLastChapter().isEmpty()?chapterAttributes.getData().getFirst().getAttributes().getChapter():mangaAttributes.getData().getAttributes().getLastChapter())
                .titulo(mangaAttributes.getData().getAttributes().getTitle().get("en"))
                .status(mangaAttributes.getData().getAttributes().getStatus())
                .uuid(mangaAttributes.getData().getId())
                .build();
    }
    public List<MangaModel> getAllMangas(){return repository.findAll();}

}
