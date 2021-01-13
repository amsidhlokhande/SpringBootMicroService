package com.amsidh.mvc.feign.client.album;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.amsidh.mvc.feign.client.album.model.AlbumResponseModel;

import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "albums-ws")
public interface AlbumsServiceClient {
    @GetMapping(value = "/albums/{userId}/albums")
    Mono<AlbumResponseModel> getAlbumsByUserId(@PathVariable("userId") String userId);
}
