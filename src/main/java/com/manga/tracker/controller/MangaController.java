package com.manga.tracker.controller;

import com.manga.tracker.dto.MangaDto;
import com.manga.tracker.service.MangadexService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
