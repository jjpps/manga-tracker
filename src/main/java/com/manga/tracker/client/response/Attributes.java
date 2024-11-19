package com.manga.tracker.client.response;

import lombok.Getter;

@Getter
public class Attributes {
    public String title;
    public String altTitles;
    public Boolean isLocked;
    public String links;
    public String originalLanguage;
    public String lastVolume;
    public String lastChapter;
    public String status;
    public String availableTranslatedLanguages;
}
