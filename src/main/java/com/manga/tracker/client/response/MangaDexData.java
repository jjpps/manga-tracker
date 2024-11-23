package com.manga.tracker.client.response;

import lombok.Getter;

@Getter
public class MangaDexData<T> {
    private String id;
    private String type;
    private T attributes;
}
