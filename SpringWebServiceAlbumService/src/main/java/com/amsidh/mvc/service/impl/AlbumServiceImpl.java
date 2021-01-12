package com.amsidh.mvc.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.amsidh.mvc.document.Album;
import com.amsidh.mvc.repository.AlbumRepository;
import com.amsidh.mvc.service.AlbumService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class AlbumServiceImpl implements AlbumService {

	private final AlbumRepository albumRepository;

	@Override
	public Mono<Album> saveAlbum(Album album) {
		log.info("AlbumServiceImpl saveAlbum called");
		return albumRepository.save(album);
	}

	@Override
	public Mono<Album> getAlbumById(String albumId) {
		log.info("AlbumServiceImpl getAlbumById called");
		return albumRepository.findById(albumId);
	}

	@Override
	public Mono<Album> updateAlbum(String albumId, Album album) {
		log.info("AlbumServiceImpl updateAlbum called");
		return getAlbumById(albumId).flatMap(alb -> {
			if (alb.getAlbumId().equals(album.getAlbumId())) {

				Optional.ofNullable(album.getAlbumName()).ifPresent(alb::setAlbumName);
				Optional.ofNullable(album.getAlbumPrice()).ifPresent(alb::setAlbumPrice);
				Optional.ofNullable(album.getUserId()).ifPresent(alb::setUserId);
				return albumRepository.save(alb);

			} else {
				return Mono.empty();
			}
		});
	}

	@Override
	public Mono<Void> deleteAlbumById(String albumId) {
		log.info("AlbumServiceImpl deleteAlbumById called");
		return albumRepository.deleteById(albumId);
	}

	@Override
	public Flux<Album> getAllAlbums() {
		log.info("AlbumServiceImpl getAllAlbums called");
		return albumRepository.findAll();
	}

	@Override
	public Flux<Album> getAlbumsByUserId(String userId) {
		log.info("AlbumServiceImpl getAlbumsByUserId called");
		return albumRepository.findByUserId(userId);
	}

}
