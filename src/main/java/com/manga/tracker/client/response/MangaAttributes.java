package com.manga.tracker.client.response;

import lombok.Getter;

@Getter
public class MangaAttributes {
    private String title;
    private String altTitles;
    private Boolean isLocked;
    private String links;
    private String originalLanguage;
    private String lastVolume;
    private String lastChapter;
    private String status;
    private String availableTranslatedLanguages;
}
