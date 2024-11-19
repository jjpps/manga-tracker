package com.manga.tracker.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value ="mangadex",url = "${mangadex.host}")
public interface MangadexClient {
}
