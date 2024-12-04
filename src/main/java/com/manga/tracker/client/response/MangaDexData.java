package com.manga.tracker.client.response;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class MangaDexData<T> {
    private String id;
    private String type;
    private T attributes;
    private List<Relationships> relationships;
}
