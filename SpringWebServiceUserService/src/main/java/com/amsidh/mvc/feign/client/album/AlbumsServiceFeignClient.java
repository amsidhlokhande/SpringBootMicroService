package com.amsidh.mvc.feign.client.album;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.amsidh.mvc.feign.client.album.model.AlbumResponseModel;

@FeignClient(name = "albums-ws", url = "albums-ws:62891")
public interface AlbumsServiceFeignClient {
	@GetMapping(value = "/albums/{userId}/albums")
	List<AlbumResponseModel> getAlbumsByUserId(@PathVariable("userId") String userId);
}
