package com.manga.tracker.controller;

import com.manga.tracker.dto.MangaDto;
import com.manga.tracker.dto.MangaRequest;
import com.manga.tracker.model.MangaModel;
import com.manga.tracker.service.MangadexService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manga")
@CrossOrigin(origins = "http://localhost:3000") // Permite somente o frontend
public class MangaController {
    @Autowired
    public MangadexService mangadexService;

    @GetMapping("/")
    public MangaDto getManga(@RequestParam String url) {
        return mangadexService.getManga(url);
    }
    @PostMapping("/")
    public MangaDto createManga(@RequestBody MangaRequest mangaRequest) {        return mangadexService.saveManga(mangaRequest);    }
    @GetMapping("/GetAll")
    public List<MangaModel> getAllManga() { return mangadexService.getAllMangas();}
    @GetMapping("/")
    public MangaDto getMangaById(@RequestParam String uuid) {
        return mangadexService.getManga(uuid);
    }

}
