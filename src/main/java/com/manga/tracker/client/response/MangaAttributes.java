package com.manga.tracker.client.response;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class MangaAttributes {
    private Map<String,String> title;
//    private String altTitles;
    private Boolean isLocked;
    private String originalLanguage;
    private String lastVolume;
    private String lastChapter;
    private String status;
    private List<String> availableTranslatedLanguages;
}
