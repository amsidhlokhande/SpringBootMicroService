package com.amsidh.mvc.service;

import com.amsidh.mvc.document.Album;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlbumService {

	@Operation(operationId = "SaveAlbum", description = "This method for saving album", tags = {
	"SaveAlbum" })
	Mono<Album> saveAlbum( @RequestBody(required = true, content = @Content(schema = @Schema(implementation = Album.class))) Album album);
	

	@Operation(operationId = "getAlbumById", description = "This method for getting album", tags = {
	"getAlbumById" })
	Mono<Album> getAlbumById(@Parameter(in = ParameterIn.PATH) String albumId);

	
	@Operation(operationId = "updateAlbum", description = "This method for update album", tags = {
	"updateAlbum" })
	Mono<Album> updateAlbum(@Parameter(in = ParameterIn.PATH) String albumId, @RequestBody(required = true, content = @Content(schema = @Schema(implementation = Album.class))) Album album);

	
	@Operation(operationId = "deleteAlbumById", description = "This method for delete album", tags = {
	"deleteAlbumById" })
	Mono<Void> deleteAlbumById(@Parameter(in = ParameterIn.PATH) String albumId);

	
	@Operation(operationId = "GetAllAlbums", description = "This method is for getting all the albums", tags = {
			"GetAllAlbums" })
	Flux<Album> getAllAlbums();

	
	@Operation(operationId = "GetAllAlbumsOfUser", description = "This method is for getting all the albums associated for a given user", tags = {
			"GetAllAlbumsOfUser" })
	Flux<Album> getAlbumsByUserId(@Parameter(in = ParameterIn.PATH) String userId);
}
