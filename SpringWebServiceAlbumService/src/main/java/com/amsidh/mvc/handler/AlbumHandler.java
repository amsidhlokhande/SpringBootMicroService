package com.amsidh.mvc.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.amsidh.mvc.document.Album;
import com.amsidh.mvc.service.AlbumService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class AlbumHandler {

	private final AlbumService albumService;

	public HandlerFunction<ServerResponse> findAllAlbums() {
		log.debug("AlbumHandler findAllAlbums called");
		return request -> ServerResponse.ok().body(albumService.getAllAlbums(), Album.class);
	}
	
	public HandlerFunction<ServerResponse> findAlbumsById() {
		log.debug("AlbumHandler findAlbumsById called");
		return request -> ServerResponse.ok().body(albumService.getAlbumById(request.pathVariable("albumId")), Album.class);
	}
	
	public HandlerFunction<ServerResponse> createAlbum() {
		log.debug("AlbumHandler findAlbumsById called");
		return request -> request.bodyToMono(Album.class).flatMap(album->ServerResponse.ok().body(albumService.saveAlbum(album), Album.class));
	}
	
	public HandlerFunction<ServerResponse> updateAlbum() {
		log.debug("AlbumHandler updateAlbum called");
		return request ->  ServerResponse.ok().body(request.bodyToMono(Album.class).flatMap(alb-> albumService.updateAlbum(request.pathVariable("albumId"), alb)), Album.class);
	}
	
	public HandlerFunction<ServerResponse> deleteAlbum() {
		log.debug("AlbumHandler deleteAlbum called");
		return request -> ServerResponse.ok().body(albumService.deleteAlbumById(request.pathVariable("albumId")), Void.class);
	}
	
	public HandlerFunction<ServerResponse> getAlbumsByUserId() {
		log.debug("AlbumHandler getAlbumsByUserId called");
		return request -> ServerResponse.ok().body(albumService.getAlbumsByUserId(request.pathVariable("userId")), Album.class);
	}
	
}
