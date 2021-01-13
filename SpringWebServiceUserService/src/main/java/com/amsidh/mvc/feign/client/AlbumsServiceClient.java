package com.amsidh.mvc.feign.client;

import com.amsidh.mvc.model.model.AlbumResponseModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "albums-ws", url = "localhost:55305")
public interface AlbumsServiceClient {
    @GetMapping(value = "/albums/{userId}/albums")
    Mono<AlbumResponseModel> getAlbumsByUserId(@PathVariable("userId") String userId);
}
