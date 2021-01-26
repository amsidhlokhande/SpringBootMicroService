package com.amsidh.mvc.config.routes;

import org.springdoc.webflux.core.fn.SpringdocRouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.amsidh.mvc.handler.AlbumHandler;
import com.amsidh.mvc.service.AlbumService;

@Configuration
public class AppRoute {

	
	private static final String ALBUMS_BASE_URI = "/albums";
	private static final String ALBUMS_WITH_ALBUMID_URI = "/albums/{albumId}";
	private static final String ALBUMS_WITH_USERID_URI = "/albums/{userId}/albums";

	@Bean
	public RouterFunction<ServerResponse> albumRoutes(AlbumHandler albumHandler) {

		return SpringdocRouteBuilder.route()
				.GET(ALBUMS_BASE_URI, RequestPredicates.accept(MediaType.APPLICATION_JSON), albumHandler.findAllAlbums(),
						builder -> builder.beanClass(AlbumService.class).beanMethod("getAllAlbums"))
				.build()
				.and(SpringdocRouteBuilder.route()
				.POST(ALBUMS_BASE_URI, RequestPredicates.accept(MediaType.APPLICATION_JSON).and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), albumHandler.createAlbum(),
						builder -> builder.beanClass(AlbumService.class).beanMethod("saveAlbum"))
				.build())
				.and(SpringdocRouteBuilder.route()
				.GET(ALBUMS_WITH_ALBUMID_URI, RequestPredicates.accept(MediaType.APPLICATION_JSON), albumHandler.findAlbumsById(),
						builder -> builder.beanClass(AlbumService.class).beanMethod("getAlbumById"))
				.build())
				.and(SpringdocRouteBuilder.route()
				.PATCH(ALBUMS_WITH_ALBUMID_URI, RequestPredicates.accept(MediaType.APPLICATION_JSON).and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), albumHandler.updateAlbum(),
						builder -> builder.beanClass(AlbumService.class).beanMethod("updateAlbum"))
				.build())
				.and(SpringdocRouteBuilder.route()
				.DELETE(ALBUMS_WITH_ALBUMID_URI, albumHandler.deleteAlbum(),
						builder -> builder.beanClass(AlbumService.class).beanMethod("deleteAlbumById"))
				.build())
				.and(SpringdocRouteBuilder.route()
				.GET(ALBUMS_WITH_USERID_URI, RequestPredicates.accept(MediaType.APPLICATION_JSON), albumHandler.getAlbumsByUserId(),
						builder -> builder.beanClass(AlbumService.class).beanMethod("getAlbumsByUserId"))
				.build());
		
	}
	
}
