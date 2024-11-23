package com.manga.tracker.client.response;

import lombok.Getter;

@Getter
public class MangaDexResponse<T> {
    public String result;
    public String response;
    public T data;

}
