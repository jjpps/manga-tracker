package com.manga.tracker.service;
import com.manga.tracker.client.MangadexClient;
import com.manga.tracker.client.response.*;
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

import static com.manga.tracker.Constants.PT_BR;

@Slf4j
@Service
@RequiredArgsConstructor
public class MangadexService {
    private final MangadexClient mangadexClient;
    private final MangaRepository repository;
    public MangaDto getManga(String url) {
        String coverFormatt = "";

        try{
            MangaDexResponse<MangaDexData<MangaAttributes>> mangaAttributes = this.mangadexClient.getManga(url.split("/")[4]);
            MangaDexResponse<List<MangaDexData<ChapterAttributes>>> chapterAttributes = this.mangadexClient.getChapters(mangaAttributes.getData().getId(),1, "desc",PT_BR);
            if(mangaAttributes.getData().getRelationships().stream().anyMatch(x-> Objects.equals(x.getType(), "cover_art"))){
                String idCover = mangaAttributes.getData().getRelationships().stream().filter(x-> Objects.equals(x.getType(), "cover_art")).findFirst().orElseThrow(() -> new IllegalStateException("Nenhum 'cover_art' encontrado!")).getId();
                MangaDexResponse<MangaDexData<CoverAttributes>> cover = this.mangadexClient.getCover(idCover);
                coverFormatt = MessageFormat.format("https://mangadex.org/covers/{0}/{1}",mangaAttributes.getData().getId(),cover.getData().getAttributes().getFileName());
            }
            return MangaDto.builder()
                    .id(0L)
                    .capLido(0L)
                    .nomeUltimoCapitulo(Objects.isNull(chapterAttributes.getData().getFirst().getAttributes().getTitle()) || chapterAttributes.getData().getFirst().getAttributes().getTitle().isEmpty() ? MessageFormat.format("Cap: {0}",chapterAttributes.getData().getFirst().getAttributes().getChapter()):chapterAttributes.getData().getFirst().getAttributes().getTitle())
                    .numeroUltimoCapitulo(chapterAttributes.getData().getFirst().getAttributes().getChapter())
                    .quantidadeCapitulos(mangaAttributes.getData().getAttributes().getLastChapter().isEmpty()?chapterAttributes.getData().getFirst().getAttributes().getChapter():mangaAttributes.getData().getAttributes().getLastChapter())
                    .titulo(mangaAttributes.getData().getAttributes().getTitle().get("en"))
                    .status(mangaAttributes.getData().getAttributes().getStatus())
                    .uuid(mangaAttributes.getData().getId())
                    .cover(coverFormatt)
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    public MangaDto getMangaByUUID(String uuid) {
        String coverFormatt = "";
        try{
            MangaDexResponse<MangaDexData<MangaAttributes>> mangaAttributes = this.mangadexClient.getManga(uuid);
            MangaDexResponse<List<MangaDexData<ChapterAttributes>>> chapterAttributes = this.mangadexClient.getChapters(mangaAttributes.getData().getId(),1, "desc",PT_BR);
            if(mangaAttributes.getData().getRelationships().stream().anyMatch(x-> Objects.equals(x.getType(), "cover_art"))){
                String idCover = mangaAttributes.getData().getRelationships().stream().filter(x-> Objects.equals(x.getType(), "cover_art")).findFirst().orElseThrow(() -> new IllegalStateException("Nenhum 'cover_art' encontrado!")).getId();
                MangaDexResponse<MangaDexData<CoverAttributes>> cover = this.mangadexClient.getCover(idCover);
                coverFormatt = MessageFormat.format("https://mangadex.org/covers/{0}/{1}",uuid,cover.getData().getAttributes().getFileName());
            }
            return MangaDto.builder()
                    .id(0L)
                    .capLido(0L)
                    .nomeUltimoCapitulo(Objects.isNull(chapterAttributes.getData().getFirst().getAttributes().getTitle()) || chapterAttributes.getData().getFirst().getAttributes().getTitle().isEmpty() ? MessageFormat.format("Cap: {0}",chapterAttributes.getData().getFirst().getAttributes().getChapter()):chapterAttributes.getData().getFirst().getAttributes().getTitle())
                    .numeroUltimoCapitulo(chapterAttributes.getData().getFirst().getAttributes().getChapter())
                    .quantidadeCapitulos(mangaAttributes.getData().getAttributes().getLastChapter().isEmpty()?chapterAttributes.getData().getFirst().getAttributes().getChapter():mangaAttributes.getData().getAttributes().getLastChapter())
                    .titulo(mangaAttributes.getData().getAttributes().getTitle().get("en"))
                    .status(mangaAttributes.getData().getAttributes().getStatus())
                    .uuid(mangaAttributes.getData().getId())
                    .cover(coverFormatt)
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    public MangaDto saveManga(MangaRequest mangaRequest){
        String coverFormatt = "";
        MangaDexResponse<MangaDexData<MangaAttributes>> mangaAttributes = this.mangadexClient.getManga(mangaRequest.getId());

        MangaDexResponse<List<MangaDexData<ChapterAttributes>>> chapterAttributes = this.mangadexClient.getChapters(mangaAttributes.getData().getId(),1, "desc",PT_BR);
        if(mangaAttributes.getData().getRelationships().stream().anyMatch(x-> Objects.equals(x.getType(), "cover_art"))){
            String idCover = mangaAttributes.getData().getRelationships().stream().filter(x-> Objects.equals(x.getType(), "cover_art")).findFirst().orElseThrow(() -> new IllegalStateException("Nenhum 'cover_art' encontrado!")).getId();
            MangaDexResponse<MangaDexData<CoverAttributes>> cover = this.mangadexClient.getCover(idCover);
            coverFormatt = MessageFormat.format("https://mangadex.org/covers/{0}/{1}",mangaAttributes.getData().getId(),cover.getData().getAttributes().getFileName());
        }
        MangaModel model = this.repository.findByUuid(mangaRequest.getId());
        if(!Objects.isNull(model)){
            return MangaDto.builder()
                    .id(model.getId())
                    .capLido(model.getCapLido())
                    .nomeUltimoCapitulo(Objects.isNull(chapterAttributes.getData().getFirst().getAttributes().getTitle()) || chapterAttributes.getData().getFirst().getAttributes().getTitle().isEmpty() ? MessageFormat.format("Cap: {0}",chapterAttributes.getData().getFirst().getAttributes().getChapter()):chapterAttributes.getData().getFirst().getAttributes().getTitle())
                    .numeroUltimoCapitulo(chapterAttributes.getData().getFirst().getAttributes().getChapter())
                    .quantidadeCapitulos(mangaAttributes.getData().getAttributes().getLastChapter().isEmpty()?chapterAttributes.getData().getFirst().getAttributes().getChapter():mangaAttributes.getData().getAttributes().getLastChapter())
                    .titulo(mangaAttributes.getData().getAttributes().getTitle().get("en"))
                    .status(mangaAttributes.getData().getAttributes().getStatus())
                    .uuid(mangaAttributes.getData().getId())
                    .cover(coverFormatt)
                    .build();
        }
        model = MangaModel.builder()
                .titulo(mangaAttributes.getData().getAttributes().getTitle().get("en"))
                .uuid(mangaAttributes.getData().getId())
                .capLido(mangaRequest.getCapLido())
                .cover(coverFormatt)
                .nomeUltimoCapitulo(Objects.isNull(chapterAttributes.getData().getFirst().getAttributes().getTitle()) || chapterAttributes.getData().getFirst().getAttributes().getTitle().isEmpty() ? MessageFormat.format("Cap: {0}",chapterAttributes.getData().getFirst().getAttributes().getChapter()):chapterAttributes.getData().getFirst().getAttributes().getTitle())
                .numeroUltimoCapitulo(chapterAttributes.getData().getFirst().getAttributes().getChapter())
                .quantidadeCapitulos(Long.parseLong(mangaAttributes.getData().getAttributes().getLastChapter().isEmpty()?chapterAttributes.getData().getFirst().getAttributes().getChapter():mangaAttributes.getData().getAttributes().getLastChapter()))
                .build();
        repository.save(model);
        return MangaDto.builder()
                .id(model.getId())
                .capLido(model.getCapLido())
                .nomeUltimoCapitulo(Objects.isNull(chapterAttributes.getData().getFirst().getAttributes().getTitle()) || chapterAttributes.getData().getFirst().getAttributes().getTitle().isEmpty() ? MessageFormat.format("Cap: {0}",chapterAttributes.getData().getFirst().getAttributes().getChapter()):chapterAttributes.getData().getFirst().getAttributes().getTitle())
                .numeroUltimoCapitulo(chapterAttributes.getData().getFirst().getAttributes().getChapter())
                .quantidadeCapitulos(mangaAttributes.getData().getAttributes().getLastChapter().isEmpty()?chapterAttributes.getData().getFirst().getAttributes().getChapter():mangaAttributes.getData().getAttributes().getLastChapter())
                .titulo(mangaAttributes.getData().getAttributes().getTitle().get("en"))
                .status(mangaAttributes.getData().getAttributes().getStatus())
                .uuid(mangaAttributes.getData().getId())
                .cover(coverFormatt)
                .build();
    }
    public List<MangaModel> getAllMangas(){return repository.findAll();}

}
