package com.amsidh.mvc.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.amsidh.mvc.document.Album;

import reactor.core.publisher.Flux;

@Repository
public interface AlbumRepository extends ReactiveMongoRepository<Album, String>{

	Flux<Album> findByUserId(String userId);
}
