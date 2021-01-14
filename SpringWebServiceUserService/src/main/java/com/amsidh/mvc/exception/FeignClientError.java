package com.amsidh.mvc.exception;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FeignClientError implements ErrorDecoder {
	private final Environment environment;

	@Override
	public Exception decode(String methodKey, Response response) {

		switch (response.status()) {
		case 400:
			if (response.status() == 400)
				return new ResponseStatusException(HttpStatus.valueOf(response.status()), response.reason());
			break;
		case 404:
			if (methodKey.contains("getAlbumsByUserId")) {
				return new ResponseStatusException(HttpStatus.valueOf(response.status()), environment.getProperty("albums.exceptions.user-album-not-found"));
			}
			break;
		default:
			return new Exception(response.reason());
		}

		return null;
	}

}
