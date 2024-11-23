package com.manga.tracker.controller;

import com.manga.tracker.dto.MangaDto;
import com.manga.tracker.service.MangadexService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manga")
public class MangaController {

    public MangadexService mangadexService;

    @GetMapping("/{id}")
    public MangaDto getManga(@PathVariable Long id) {
        return mangadexService.getManga(id);
    }
}
