package com.manga.tracker.client;

import com.manga.tracker.client.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@FeignClient(value ="mangadex",url = "${mangadex.host}")
public interface MangadexClient {
    @GetMapping("/manga/{id}")
    MangaDexResponse<MangaDexData<MangaAttributes>> getManga(@PathVariable("id") final String mangaId);
    @GetMapping("/chapter")
    MangaDexResponse<List<MangaDexData<ChapterAttributes>>> getChapters(@RequestParam("manga") final String mangaId, @RequestParam("limit") final int limit,
                                                                        @RequestParam("order[chapter]") final String order, @RequestParam("translatedLanguage[]") final String translatedLanguage);
    @GetMapping("/cover/{id}")
    MangaDexResponse<MangaDexData<CoverAttributes>> getCover(@PathVariable("id") final String mangaId);



}
